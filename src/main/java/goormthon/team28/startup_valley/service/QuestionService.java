package goormthon.team28.startup_valley.service;


import goormthon.team28.startup_valley.domain.Member;
import goormthon.team28.startup_valley.domain.Question;
import goormthon.team28.startup_valley.domain.Team;
import goormthon.team28.startup_valley.domain.User;
import goormthon.team28.startup_valley.dto.response.QuestionDto;
import goormthon.team28.startup_valley.dto.response.QuestionListDto;
import goormthon.team28.startup_valley.dto.type.EQuestionStatus;
import goormthon.team28.startup_valley.exception.CommonException;
import goormthon.team28.startup_valley.exception.ErrorCode;
import goormthon.team28.startup_valley.repository.MemberRepository;
import goormthon.team28.startup_valley.repository.QuestionRepository;
import goormthon.team28.startup_valley.repository.TeamRepository;
import goormthon.team28.startup_valley.repository.UserRepository;
import goormthon.team28.startup_valley.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final MemberRepository memberRepository;
    private final TeamService teamService;
    private final MemberService memberService;
    private final UserService userService;
    @Transactional
    public Question saveQuestion(String guildId, String senderId, String receiverId, String content, LocalDateTime time){
        String code = NumberUtil.generateRandomCode();
        while(questionRepository.existsByCode(code)){
            code = NumberUtil.generateRandomCode();
        }
        Team findTeam = teamService.findByGuildId(guildId);
        return questionRepository.save(Question.builder()
                        .sender(memberService.findByTeamAndUser(findTeam, userService.findBySerialId(senderId)))
                        .receiver(memberService.findByTeamAndUser(findTeam, userService.findBySerialId(receiverId)))
                        .content(content)
                        .status(EQuestionStatus.WAITING_ANSWER)
                        .createdAt(time)
                        .code(code)
                .build()
        );
    }

    public QuestionListDto listWaitingQuestion(Long userId, Long teamsId) {
        User currentUser = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));

        if (teamRepository.existsById(teamsId))
            throw new CommonException(ErrorCode.NOT_FOUND_TEAM);

        Member member = memberRepository.findByIdAndUser(teamsId, currentUser)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MEMBER));

        List<Question> questionList = questionRepository
                .findByReceiverAndStatus(member, EQuestionStatus.WAITING_ANSWER);
        List<QuestionDto> questionDtoList = questionList.stream()
                .map(question -> QuestionDto.of(
                        question.getId(),
                        question.getContent(),
                        question.getReceiver().getPart(),
                        question.getSender().getUser().getProfileImage()
                )).toList();

        return QuestionListDto.of(questionDtoList, questionList.size());
    }
}
