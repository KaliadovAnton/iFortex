package com.ifortex.bookservice.service.impl;

import com.ifortex.bookservice.model.Book;
import com.ifortex.bookservice.model.Member;
import com.ifortex.bookservice.repository.MemberRepository;
import com.ifortex.bookservice.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

//due to the fact that I can not modify Member class, can not modify application configuration and can not write query with fetch (limitations of this task) there is a "n+1" problem
@Primary
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    private final String ROMANCE = "Romance";

    @Override
    @Transactional(readOnly = true)
    public Member findMember() {
        var members = memberRepository.findAll();
        var theOldestRomanceBook = members.stream().flatMap(member -> member.getBorrowedBooks().stream())
                .filter(book -> book.getGenres().contains(ROMANCE))
                .min(Comparator.comparing(Book::getPublicationDate))
                .orElseThrow();
        return members.stream()
                .filter(member -> member.getBorrowedBooks().contains(theOldestRomanceBook))
                .max(Comparator.comparing(Member::getMembershipDate)).orElseThrow();
    }

    @Override
    @Transactional
    public List<Member> findMembers() {
        return memberRepository.findAll().stream()
                .filter(member -> registeredIn2023(member) && member.getBorrowedBooks().isEmpty())
                .toList();
    }

    private boolean registeredIn2023(Member member) {
        return member.getMembershipDate().isAfter(LocalDateTime.of(2023, 1,1,0,0))
                && member.getMembershipDate().isBefore((LocalDateTime.of(2024,1,1,0,0)));
    }
}
