package com.example.algostudy.listener;

import com.example.algostudy.domain.entity.*;
import com.example.algostudy.repository.*;
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

    private static AtomicInteger count = new AtomicInteger(0);


    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }

        setupSecurityResources();
        setupMission();


        alreadySetup = true;
    }

    private void setupMission() {
        Mission mission = new Mission();
        mission.setMissionName("1일 1백준 챌린지");
        mission.setMissionDesc("알고리즘은 꾸준함이 생명! 매일 백준 알고리즘 사이트(acmicpc.net)에서 한문제씩 풀기");
        mission.setImagePath("https://bucketforkoji.s3.ap-northeast-2.amazonaws.com/algostudy/boj210.png");

        Mission save = missionRepository.save(mission);
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

        saveRoleWithResourceList(adminRole, adminResourceList);

        Role memberRole = createRoleIfNotFound("ROLE_MEMBER", "일반회원");
        List<Resource> memberResourceList = new ArrayList<>();
        memberResourceList.add(memberAllResource);

        saveRoleWithResourceList(memberRole, memberResourceList);

        Member adminMember = newMember(adminRole, "admin", "admin", passwordEncoder.encode("admin"), "koji4321");
        Member normalMember1 = newMember(memberRole, "member1", "member1", passwordEncoder.encode("member1"), "koji4321");
        Member normalMember2 = newMember(memberRole, "member2", "member2", passwordEncoder.encode("member2"), "koji4321");
        Member normalMember3 = newMember(memberRole, "member3", "member3", passwordEncoder.encode("member3"), "koji4321");





    }

    public Member newMember(Role role, String username, String email, String password, String bojId) {
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
    public void saveRoleWithResourceList(Role role, List<Resource> resourceList) {
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
