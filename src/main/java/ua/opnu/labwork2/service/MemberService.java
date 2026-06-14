package ua.opnu.labwork2.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.opnu.labwork2.exception.ConflictOperationException;
import ua.opnu.labwork2.exception.DuplicateResourceException;
import ua.opnu.labwork2.exception.ResourceNotFoundException;
import ua.opnu.labwork2.model.Member;
import ua.opnu.labwork2.model.RegistrationStatus;
import ua.opnu.labwork2.repository.MemberRepository;
import ua.opnu.labwork2.repository.RegistrationRepository;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final RegistrationRepository registrationRepository; // Додано для перевірки зв'язків

    public MemberService(MemberRepository memberRepository, RegistrationRepository registrationRepository) {
        this.memberRepository = memberRepository;
        this.registrationRepository = registrationRepository;
    }

    @Transactional
    public Member create(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            throw new DuplicateResourceException("Member with email '" + member.getEmail() + "' already exists.");
        }
        return memberRepository.save(member);
    }

    public List<Member> getAll() {
        return memberRepository.findAll();
    }

    public Member getById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
    }

    @Transactional
    public Member update(Long id, Member memberDetails) {
        Member member = getById(id);

        if (memberRepository.existsByEmailAndIdNot(memberDetails.getEmail(), id)) {
            throw new DuplicateResourceException("Email '" + memberDetails.getEmail() + "' is already taken by another member.");
        }

        member.setFirstName(memberDetails.getFirstName());
        member.setLastName(memberDetails.getLastName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());

        return memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) {
            throw new ResourceNotFoundException("Member not found with id: " + id);
        }

        boolean hasActiveRegistrations = registrationRepository.existsActiveByMember(id, RegistrationStatus.ACTIVE);
        if (hasActiveRegistrations) {
            throw new ConflictOperationException("Cannot delete member: the member has active workout registrations.");
        }

        memberRepository.deleteById(id);
    }

    public List<Member> getByWorkoutId(Long workoutId) {
        return memberRepository.findByWorkouts_Id(workoutId);
    }
}
