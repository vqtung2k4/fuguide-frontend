package com.fptdoandemo.fuguide;

import com.fptdoandemo.fuguide.model.CampusLife;
import com.fptdoandemo.fuguide.model.News;
import com.fptdoandemo.fuguide.model.Program;
import com.fptdoandemo.fuguide.repository.CampusLifeRepository;
import com.fptdoandemo.fuguide.repository.NewsRepository;
import com.fptdoandemo.fuguide.repository.ProgramRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class FuguideApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuguideApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(ProgramRepository programRepo, NewsRepository newsRepo, CampusLifeRepository campusRepo) {
        return args -> {
            programRepo.save(new Program(null, "Engineering", "Chương trình đào tạo kỹ sư hiện đại.", "bi-gear"));
            programRepo.save(new Program(null, "Business", "Đào tạo nhà quản trị tương lai.", "bi-graph-up"));

            campusRepo.save(new CampusLife(null, "Student Organizations", "Over 100+ clubs and student-led organizations to join.", "bi-people-fill"));
            campusRepo.save(new CampusLife(null, "Modern Facilities", "State-of-the-art libraries, labs, and recreational spaces.", "bi-house-heart-fill"));
            campusRepo.save(new CampusLife(null, "Global Opportunities", "Exchange programs with 200+ partner universities worldwide.", "bi-globe-americas"));

            newsRepo.save(new News(null, "Achievement", "Sinh viên FPT đạt giải quốc gia", "Mô tả ngắn...", LocalDate.now()));
            System.out.println("Đã khởi tạo dữ liệu mẫu thành công!");
        };
    }
}
