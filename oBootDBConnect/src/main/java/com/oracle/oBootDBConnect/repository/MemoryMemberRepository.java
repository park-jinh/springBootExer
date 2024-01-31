package com.oracle.oBootDBConnect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConnect.domain.Member1;


public class MemoryMemberRepository implements MemberRepository {
	private static Map<Long, Member1> store = new HashMap<Long,Member1>();
	private static long sequence = 0L;
	
	@Override
	public Member1 save(Member1 member1) {
		System.out.println("MemoryMemberRepository savs start...");
		member1.setId(++sequence);
		store.put(member1.getId(), member1);
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll start...");
		return new ArrayList<Member1>(store.values());
	}

}
