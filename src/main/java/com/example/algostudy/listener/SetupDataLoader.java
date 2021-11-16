package com.example.algostudy.listener;

import com.example.algostudy.domain.entity.*;
import com.example.algostudy.repository.*;
import com.example.algostudy.repository.Image.ImageRepository;
import com.example.algostudy.repository.Member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private RoleResourceRepository roleResourceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private ImageRepository imageRepository;

    private static AtomicInteger count = new AtomicInteger(0);


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) {
            return;
        }

        setupSecurityResources();
        saveMissionIfNotFound();

        alreadySetup = true;
    }


    private void saveMissionIfNotFound() {

    }

    private void setupSecurityResources() {
        Resource adminAllResource = createAndSaveResourceIfNotFound("/admin/**");
        Resource memberAllResource= createAndSaveResourceIfNotFound("/users/**");
        Resource homeResource= createAndSaveResourceIfNotFound("/");
        Resource loginResource= createAndSaveResourceIfNotFound("/login");
        Resource registerResource= createAndSaveResourceIfNotFound("/register");


        Role adminRole = createRoleIfNotFound("ROLE_ADMIN", "관리자");
        List<Resource> adminResourceList = new ArrayList<>();
        adminResourceList.add(adminAllResource);

        saveRoleWithResourceListIfNotFound(adminRole, adminResourceList);

        Role memberRole = createRoleIfNotFound("ROLE_MEMBER", "일반회원");
        List<Resource> memberResourceList = new ArrayList<>();
        memberResourceList.add(memberAllResource);

        saveRoleWithResourceListIfNotFound(memberRole, memberResourceList);

        Member adminMember = createMemberIfNotFound(adminRole, "admin", "admin", passwordEncoder.encode("admin"), "koji4321");
        Member normalMember1 = createMemberIfNotFound(memberRole, "member1", "member1", passwordEncoder.encode("member1"), "sjoh0704");
        Member normalMember2 = createMemberIfNotFound(memberRole, "member2", "member2", passwordEncoder.encode("member2"), "koji4321");
        Member normalMember3 = createMemberIfNotFound(memberRole, "member3", "member3", passwordEncoder.encode("member3"), "koji4321");

        Mission findMission = missionRepository.findByMissionName("1일 1백준 챌린지");
        if (findMission != null) {
            return;
        }
        Mission bojMission = new Mission();
        bojMission.setMissionName("1일 1백준 챌린지");
        bojMission.setMissionDesc("알고리즘은 꾸준함이 생명! 매일 백준 알고리즘 사이트(acmicpc.net)에서 한문제씩 풀기");
        bojMission.setImagePath("https://bucketforkoji.s3.ap-northeast-2.amazonaws.com/algostudy/boj210.png");

        missionRepository.save(bojMission);


        Mission zoomMission = new Mission();
        zoomMission.setMissionName("내가 알려주지");
        zoomMission.setMissionDesc("모르는게 있나? 나한테 물어보게나! 선정한 주제에 대해 번갈아가며 실시간으로 강의하기");
        zoomMission.setImagePath("https://bucketforkoji.s3.ap-northeast-2.amazonaws.com/algostudy/zoom.png");

        missionRepository.save(zoomMission);


        Image defaultImage = Image.builder()
                .imagePath("/home/koji/Spring Projects/Algostudy/savedImages/madprodo.png")
                .imageUrl("https://bucketforkoji.s3.ap-northeast-2.amazonaws.com/algostudy/5c5da6d9-bd40-4e39-a488-eac2778ff8e1.png")
                .build();

        imageRepository.save(defaultImage);


    }

    public Member createMemberIfNotFound(Role role, String username, String email, String password, String bojId) {
        Member findMember = memberRepository.findByUsername(username);
        if (findMember != null) {
            return findMember;
        }
        Member member = Member.builder()
                .username(username)
                .password(password)
                .email(email)
                .bojId(bojId)
                .build();
        List<Role> roleList = new ArrayList<>();
        roleList.add(role);

        saveMemberWithRoleList(member, roleList);
        return member;
    }

    @Transactional
    public void saveMemberWithRoleList(Member member, List<Role> roleList) {
        for (Role role : roleList) {
            MemberRole memberRole = MemberRole.builder()
                    .member(member)
                    .role(role)
                    .build();
            memberRoleRepository.save(memberRole);
            Role findRole = roleRepository.findByRoleName(role.getRoleName());
            if (findRole.getMemberRoleList() == null) {
                findRole.setMemberRoleList(new ArrayList<>());
            }
            findRole.getMemberRoleList().add(memberRole);
            if (member.getMemberRoleList() == null) {
                member.setMemberRoleList(new ArrayList<>());
            }
            member.getMemberRoleList().add(memberRole);
        }
        memberRepository.save(member);
    }

    @Transactional
    public void saveRoleWithResourceListIfNotFound(Role role, List<Resource> resourceList) {
        Role findRole = roleRepository.findByRoleName(role.getRoleName());
        if (findRole != null) {
            return;
        }
        for (Resource resource : resourceList) {
            RoleResource roleResource = RoleResource.builder()
                    .role(role)
                    .resource(resource)
                    .build();
            RoleResource saveRoleResource = roleResourceRepository.save(roleResource);
            Resource findResource = resourceRepository.findByResourceName(resource.getResourceName());
            if (findResource.getRoleResourceList() == null) {
                findResource.setRoleResourceList(new ArrayList<>());
            }
            findResource.getRoleResourceList().add(saveRoleResource);
            if (role.getRoleResourceList() == null) {
                role.setRoleResourceList(new ArrayList<>());
            }
            role.getRoleResourceList().add(saveRoleResource);
        }
        roleRepository.save(role);
    }

    private Resource createAndSaveResourceIfNotFound(String resourceName) {
        Resource resource = resourceRepository.findByResourceName(resourceName);
        if (resource == null) {

            resource = Resource.builder()
                    .resourceName(resourceName)
                    .orderNum(count.incrementAndGet())
                    .build();
        }
        return resourceRepository.save(resource);
    }

    public Role createRoleIfNotFound(String roleName, String roleDesc) {
        Role role = roleRepository.findByRoleName(roleName);
        if (role == null) {
            role = role.builder()
                    .roleName(roleName)
                    .roleDesc(roleDesc)
                    .build();
        }
        return role;
    }
}
