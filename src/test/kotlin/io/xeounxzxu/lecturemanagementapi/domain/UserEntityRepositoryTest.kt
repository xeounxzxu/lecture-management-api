package io.xeounxzxu.lecturemanagementapi.domain

import io.xeounxzxu.lecturemanagementapi.mock.UserEntityMock
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.jdbc.Sql
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@DataJpaTest
class UserEntityRepositoryTest {

    @Autowired
    private lateinit var userEntityRepository: UserEntityRepository

    @Test
    fun `정상적으로 저장이 된다`() {
        val mock = UserEntityMock.entity(id = null)
        val actual = userEntityRepository.save(mock)
        assertEquals(mock, actual)
    }

    @Test
    @Sql(scripts = ["/sql/UserEntity.sql"])
    fun `이메일 기준으로 조회시 정상적으로 조회를 한다`() {
        val email = "hong@example.com"
        val actual = userEntityRepository.findByEmail(email)
        assertNotNull(actual)
    }
}
