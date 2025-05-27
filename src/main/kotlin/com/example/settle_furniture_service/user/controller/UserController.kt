package com.example.settle_furniture_service.user.controller

import com.example.settle_furniture_service.common.docs.SwaggerDocs
import com.example.settle_furniture_service.user.dto.SignUpInfo
import com.example.settle_furniture_service.user.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * 사용자 관련 API를 처리하는 컨트롤러
 */
@Tag(name = "User", description = "사용자 관련 API")
@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    /**
     * 회원가입 API
     * @param request 회원가입 요청 정보
     * @return 회원가입 결과
     */
    @Operation(
        summary = SwaggerDocs.User.SUMMARY_SIGNUP,
        description = SwaggerDocs.User.DESCRIPTION_SIGNUP
    )
    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpInfo.Request
    ): ResponseEntity<SignUpInfo.Response> {
        val response = userService.signUp(request)
        return ResponseEntity.ok(response)
    }

    /**
     * 이메일로 사용자 조회 API
     * @param email 조회할 사용자의 이메일
     * @return 사용자 정보
     */
    @Operation(
        summary = SwaggerDocs.User.SUMMARY_FIND_BY_EMAIL,
        description = SwaggerDocs.User.DESCRIPTION_FIND_BY_EMAIL
    )
    @GetMapping("/email/{email}")
    fun findByEmail(
        @Parameter(description = SwaggerDocs.User.PARAM_EMAIL, required = true)
        @PathVariable email: String
    ): ResponseEntity<SignUpInfo.Response> {
        val response = userService.findByEmail(email)
        return ResponseEntity.ok(response)
    }

    /**
     * ID로 사용자 조회 API
     * @param id 조회할 사용자의 ID
     * @return 사용자 정보
     */
    @Operation(
        summary = SwaggerDocs.User.SUMMARY_FIND_BY_ID,
        description = SwaggerDocs.User.DESCRIPTION_FIND_BY_ID
    )
    @GetMapping("/{id}")
    fun findById(
        @Parameter(description = SwaggerDocs.User.PARAM_ID, required = true)
        @PathVariable id: Long
    ): ResponseEntity<SignUpInfo.Response> {
        val response = userService.findById(id)
        return ResponseEntity.ok(response)
    }
}