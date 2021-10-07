package com.example.algostudy.controller;

import com.example.algostudy.common.CommonTools;
import com.example.algostudy.domain.dto.MissionDto;
import com.example.algostudy.domain.entity.Member;
import com.example.algostudy.domain.entity.Mission;
import com.example.algostudy.mapper.MissionMapper;
import com.example.algostudy.repository.MissionRepository;
import com.example.algostudy.service.MissionService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final MissionMapper missionMapper = Mappers.getMapper(MissionMapper.class);
    private final MissionService missionService;
    private final CommonTools commonTools;

    @GetMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        List<Mission> missionList = missionService.findAll();
        List<MissionDto> missionDtoList = missionList.stream().map(missionMapper::toDto).collect(Collectors.toList());
        model.addAttribute("missionList", missionDtoList);
        return "study-new";
    }

    @PostMapping("/study/new")
    public String addStudy(@AuthenticationPrincipal Member member,
                           @RequestParam("mainImage") MultipartFile mainImage,
                           HttpServletRequest request, Model model) {
        model.addAttribute("member", member);
        commonTools.printParams(request);
        System.out.println("mainImage = " + mainImage);
        return "study-new";
    }



    @GetMapping("/study/search")
    public String searchStudy(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("member", member);
        return "study-search";
    }
}
