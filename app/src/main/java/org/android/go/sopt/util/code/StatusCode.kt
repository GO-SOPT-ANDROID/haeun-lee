package org.android.go.sopt.util.code

// 회원가입
const val CODE_INCORRECT_INPUT = 400 // 아이디 또는 비밀번호가 일치하지 않는 경우
const val CODE_DUPLICATED_ID = 409   // 아이디가 중복된 경우

// 로그인
const val CODE_UNREGISTERED_USER = 100 // prefs에 저장된 유저 데이터가 없는 경우
const val CODE_INVALID_INPUT = 101     // 입력값이 유효하지 않은 경우 (자릿수 등)
