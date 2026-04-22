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

@SpringBootApplication
public class FuguideApplication {

	public static void main(String[] args) {
		SpringApplication.run(FuguideApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(ProgramRepository programRepo, NewsRepository newsRepo, CampusLifeRepository campusRepo) {
        return args -> {
            if (programRepo.findAll().isEmpty()) {
                programRepo.save(new Program(null, "Engineering", "Chuong trinh dao tao ky su hien dai.", "bi-gear"));
                programRepo.save(new Program(null, "Business", "Dao tao nha quan tri tuong lai.", "bi-graph-up"));
            }

            if (campusRepo.findAll().isEmpty()) {
                campusRepo.save(new CampusLife(null, "Student Organizations", "Over 100+ clubs and student-led organizations to join.", "bi-people-fill"));
                campusRepo.save(new CampusLife(null, "Modern Facilities", "State-of-the-art libraries, labs, and recreational spaces.", "bi-house-heart-fill"));
                campusRepo.save(new CampusLife(null, "Global Opportunities", "Exchange programs with 200+ partner universities worldwide.", "bi-globe-americas"));
            }

            if (newsRepo.findAll().isEmpty()) {
                newsRepo.save(new News(null, "Achievement", "Sinh vien FPT dat giai quoc gia", "Mo ta ngan...", "2026-04-22"));
                newsRepo.save(new News(null, "Achievement", "abc", "Mo ta sieu ngan...", "2026-04-21"));
            }
        };
    }
}
