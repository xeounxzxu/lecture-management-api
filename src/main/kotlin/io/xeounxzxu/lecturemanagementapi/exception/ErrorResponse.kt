package io.xeounxzxu.lecturemanagementapi.exception

data class ErrorResponse(val message: String) {

    companion object {

        /**
         * 기본 에러메세지 정의
         */
        const val DEFAULT_ERROR = "처리 중 오류가 발생이 되었습니다."
        const val DEFAULT_BAD_REQUEST = "잘못된 요청입니다."

        fun toException(): ErrorResponse {
            return ErrorResponse(
                message = DEFAULT_ERROR
            )
        }

        fun toBadRequest(
            message: String? = null
        ): ErrorResponse {
            return ErrorResponse(
                message = message ?: DEFAULT_BAD_REQUEST
            )
        }
    }
}
