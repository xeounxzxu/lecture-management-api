package io.xeounxzxu.lecturemanagementapi.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LectureEntityRepository : JpaRepository<LectureEntity, Long>
