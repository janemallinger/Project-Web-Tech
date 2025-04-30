//package com.example.frogcrew.crewmember.auth;
//
//import com.example.frogcrew.crewmember.model.CrewMember;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//
//public class CrewMemberPrincipal implements UserDetails {
//
//    private CrewMember member;
//
//    public CrewMemberPrincipal(CrewMember member) {
//        this.member = member;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
//
//    @Override
//    public String getPassword() {
//        return this.member.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return this.member.getEmail();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
//    }
//
//    public CrewMember getMember() {
//        return member;
//    }
//}
