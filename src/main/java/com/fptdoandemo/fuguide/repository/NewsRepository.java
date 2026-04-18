package com.fptdoandemo.fuguide.repository;

import com.fptdoandemo.fuguide.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByOrderByPublishedDateDesc();
}
