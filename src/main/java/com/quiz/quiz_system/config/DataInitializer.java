package com.quiz.quiz_system.config;

import com.quiz.quiz_system.entity.*;
import com.quiz.quiz_system.repository.QuizRepository;
import com.quiz.quiz_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Create admin user if not exists
        if (!userRepository.existsByUsername("admin")) {
            User admin = User.builder()
                    .username("admin")
                    .email("admin@quiz.com")
                    .password(passwordEncoder.encode("admin123"))
                    .fullName("System Administrator")
                    .role(Role.ADMIN)
                    .build();
            userRepository.save(admin);
            System.out.println("Admin user created: admin / admin123");
        }

        // Create sample user if not exists
        if (!userRepository.existsByUsername("student")) {
            User student = User.builder()
                    .username("student")
                    .email("student@quiz.com")
                    .password(passwordEncoder.encode("student123"))
                    .fullName("Sample Student")
                    .role(Role.USER)
                    .build();
            userRepository.save(student);
            System.out.println("Sample user created: student / student123");
        }

        // Create sample quiz if none exists
        if (quizRepository.count() == 0) {
            User admin = userRepository.findByUsername("admin").orElse(null);
            if (admin != null) {
                createSampleQuiz(admin);
                System.out.println("Sample quiz created!");
            }
        }
    }

    private void createSampleQuiz(User admin) {
        Quiz quiz = Quiz.builder()
                .title("Java Fundamentals Quiz")
                .description("Test your knowledge of Java programming basics")
                .timeLimitMinutes(15)
                .totalMarks(10.0)
                .passingMarks(6.0)
                .negativeMarking(true)
                .negativeMarkValue(0.25)
                .isActive(true)
                .createdBy(admin.getId())
                .build();

        // Question 1
        Question q1 = Question.builder()
                .questionText("What is the default value of an int variable in Java?")
                .marks(2.0)
                .correctKey("A")
                .quiz(quiz)
                .build();
        q1.setOptions(Arrays.asList(
                Option.builder().optionKey("A").optionText("0").question(q1).build(),
                Option.builder().optionKey("B").optionText("null").question(q1).build(),
                Option.builder().optionKey("C").optionText("undefined").question(q1).build(),
                Option.builder().optionKey("D").optionText("-1").question(q1).build()
        ));

        // Question 2
        Question q2 = Question.builder()
                .questionText("Which keyword is used to inherit a class in Java?")
                .marks(2.0)
                .correctKey("B")
                .quiz(quiz)
                .build();
        q2.setOptions(Arrays.asList(
                Option.builder().optionKey("A").optionText("implements").question(q2).build(),
                Option.builder().optionKey("B").optionText("extends").question(q2).build(),
                Option.builder().optionKey("C").optionText("inherits").question(q2).build(),
                Option.builder().optionKey("D").optionText("super").question(q2).build()
        ));

        // Question 3
        Question q3 = Question.builder()
                .questionText("What is the size of int data type in Java?")
                .marks(2.0)
                .correctKey("C")
                .quiz(quiz)
                .build();
        q3.setOptions(Arrays.asList(
                Option.builder().optionKey("A").optionText("16 bits").question(q3).build(),
                Option.builder().optionKey("B").optionText("8 bits").question(q3).build(),
                Option.builder().optionKey("C").optionText("32 bits").question(q3).build(),
                Option.builder().optionKey("D").optionText("64 bits").question(q3).build()
        ));

        // Question 4
        Question q4 = Question.builder()
                .questionText("Which method is the entry point of a Java application?")
                .marks(2.0)
                .correctKey("A")
                .quiz(quiz)
                .build();
        q4.setOptions(Arrays.asList(
                Option.builder().optionKey("A").optionText("main()").question(q4).build(),
                Option.builder().optionKey("B").optionText("start()").question(q4).build(),
                Option.builder().optionKey("C").optionText("init()").question(q4).build(),
                Option.builder().optionKey("D").optionText("run()").question(q4).build()
        ));

        // Question 5
        Question q5 = Question.builder()
                .questionText("What is JVM?")
                .marks(2.0)
                .correctKey("D")
                .quiz(quiz)
                .build();
        q5.setOptions(Arrays.asList(
                Option.builder().optionKey("A").optionText("Java Very Large Memory").question(q5).build(),
                Option.builder().optionKey("B").optionText("Java Variable Machine").question(q5).build(),
                Option.builder().optionKey("C").optionText("Java Verified Machine").question(q5).build(),
                Option.builder().optionKey("D").optionText("Java Virtual Machine").question(q5).build()
        ));

        quiz.setQuestions(Arrays.asList(q1, q2, q3, q4, q5));
        quizRepository.save(quiz);
    }
}

