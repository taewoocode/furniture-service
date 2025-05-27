package com.example.settle_furniture_service.user.controller

import com.example.settle_furniture_service.user.dto.FindUserByEmailInfo
import com.example.settle_furniture_service.user.dto.FindUserByIdInfo
import com.example.settle_furniture_service.user.dto.SignUpInfo
import com.example.settle_furniture_service.user.dto.UserLoginInfo
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
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    @Operation(
        summary = "회원가입",
        description = "새로운 사용자를 등록합니다"
    )
    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpInfo.SignUpInfoRequest
    ): ResponseEntity<SignUpInfo.SignUpInfoResponse> {
        return ResponseEntity.ok(userService.signUp(request))
    }


    @Operation(
        summary = "로그인",
        description = "이메일과 비밀번호로 로그인합니다"
    )
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: UserLoginInfo.UserLoginInfoRequest
    ): ResponseEntity<UserLoginInfo.UserLoginInfoResponse> {
        return ResponseEntity.ok(userService.login(request))
    }


    @Operation(
        summary = "이메일로 사용자 조회",
        description = "이메일로 사용자 정보를 조회합니다"
    )
    @PostMapping("/email/{email}")
    fun findByEmail(
        @Parameter(description = "사용자 이메일", required = true)
        @Valid @RequestBody request: FindUserByEmailInfo.FindUserByEmailRequest
    ): ResponseEntity<FindUserByEmailInfo.FindUserByEmailResponse> {
        val findByEmail: FindUserByEmailInfo.FindUserByEmailResponse = userService.findByEmail(request)
        return ResponseEntity.ok(findByEmail)
    }


    @Operation(
        summary = "ID로 사용자 조회",
        description = "ID로 사용자 정보를 조회합니다"
    )
    @GetMapping("/{id}")
    fun findById(
        @Parameter(description = "사용자 ID", required = true)
        @PathVariable id: Long
    ): ResponseEntity<FindUserByIdInfo.FindUserByIdResponse> {
        val request = FindUserByIdInfo.FindUserByIdRequest(id = id)
        return ResponseEntity.ok(userService.findById(request))
    }
}