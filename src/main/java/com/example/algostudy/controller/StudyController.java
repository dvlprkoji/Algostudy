package com.example.algostudy.controller;

import com.example.algostudy.common.CommonTools;
import com.example.algostudy.domain.dto.MissionDto;
import com.example.algostudy.domain.dto.MissionForm;
import com.example.algostudy.domain.dto.TeamRegisterForm;
import com.example.algostudy.domain.entity.Image;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.mapper.MissionMapper;
import com.example.algostudy.service.ImageService;
import com.example.algostudy.service.MissionService;
import com.example.algostudy.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final MissionMapper missionMapper = Mappers.getMapper(MissionMapper.class);
    private final MissionService missionService;
    private final ImageService imageService;
    private final CommonTools commonTools;
    private final TeamService teamService;

    @GetMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        List<Mission> missionList = missionService.findAll();
        List<MissionDto> missionDtoList = missionList.stream().map(missionMapper::toDto).collect(Collectors.toList());
        model.addAttribute("missionList", missionDtoList);
        model.addAttribute("missionForm", new MissionForm());
        model.addAttribute("teamRegisterForm", new TeamRegisterForm());
        return "study-new";
    }

    @PostMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member,
                           @RequestParam("mainImage") MultipartFile mainImage,
                           @Valid @ModelAttribute("teamRegisterForm") TeamRegisterForm teamRegisterForm,
                           BindingResult teamBindingResult,
                           @Valid @ModelAttribute("missionForm") MissionForm missionForm,
                           BindingResult missionBindingResult,
                           HttpServletRequest request, Model model) throws IOException {
        model.addAttribute("member", member);
        if (teamBindingResult.hasErrors() || missionBindingResult.hasErrors()) {
            List<Mission> missionList = missionService.findAll();
            List<MissionDto> missionDtoList = missionList.stream().map(missionMapper::toDto).collect(Collectors.toList());
            model.addAttribute("missionList", missionDtoList);
        }
        teamService.checkUrl(teamRegisterForm, teamBindingResult);

        if (teamBindingResult.hasErrors() || missionBindingResult.hasErrors()) {
            return "study-new";
        }

        Image saveImage;
        if (!mainImage.isEmpty()) {
            String path = imageService.upload(mainImage, "algostudy");
            saveImage = imageService.saveImage(mainImage, path);
        }
        else{
            saveImage = imageService.getDefaultTeamImage();
        }

        teamService.createTeam(member, teamRegisterForm, missionForm, saveImage);





        commonTools.printParams(request);
        System.out.println("teamRegisterForm = " + teamRegisterForm);
        System.out.println("missionForm = " + missionForm);
        System.out.println("teamBindingResult = " + teamBindingResult);
        System.out.println("missionBindingResult = " + missionBindingResult);
        System.out.println("mainImage = " + mainImage);
        return "redirect:/";
    }



    @GetMapping("/study/search")
    public String searchStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        return "study-search";
    }

    @GetMapping("/study/mystudy")
    public String myStudy(@AuthenticationPrincipal Member member, Model model) {
        if (member.getTeam().getStatus().equals("beforeStart")) {
            return "recruit";
        }
        return "mystudy";
    }
}
