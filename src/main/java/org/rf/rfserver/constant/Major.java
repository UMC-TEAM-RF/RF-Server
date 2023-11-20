package org.rf.rfserver.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@RequiredArgsConstructor
@Getter
public enum Major implements EnumModel{
    MECHANICAL("기계공학과")
    ,AEROSPACE("항공우주공학과")
    ,SHIPBUILDING("조선해양공학과")
    ,INDUSTRIAL_MANAGEMENT("산업경영공학과")
    ,CHEMICAL("화학공학과")
    ,BIOTECHNOLOGY("생명공학과")
    ,POLYMER("고분자공학과")
    ,NEW_MATERIAL("신소재공학과")
    ,SOCIAL_INFRASTRUCTURE("사회인프라공학과")
    ,ENVIRONMENTAL("환경공학과")
    ,SPATIAL_INFORMATION("공간정보공학과")
    ,ARCHITECTURAL("건축공학")
    ,CONSTRUCTION("건축학")
    ,ENERGY_RESOURCES("에너지자원공학과")
    ,ELECTRICAL("전기공학과")
    ,ELECTRONIC("전자공학과")
    ,INFORMATION_COMMUNICATION("정보통신공학과")
    ,MATHEMATICS("수학과")
    ,STATISTICS("통계학과")
    ,PHYSICS("물리학과")
    ,CHEMISTRY("화학과")
    ,LIFE_SCIENCES("생명과학과")
    ,OCEAN_SCIENCE("해양과학과")
    ,FOOD_NUTRITION("식품영양학과")
    ,BUSINESS_ADMINISTRATION("경영학과")
    ,GLOBAL_FINANCE("글로벌금융학과")
    ,ASIA_PACIFIC_LOGISTICS("아태물류학부")
    ,INTERNATIONAL_TRADE("국제통상학과")
    ,KOREAN_LANGUAGE_EDUCATION("국어교육과")
    ,ENGLISH_EDUCATION("영어교육과")
    ,SOCIAL_EDUCATION("사회교육과")
    ,PHYSICAL_EDUCATION("체육교육과")
    ,EDUCATION("교육학과")
    ,MATHEMATICS_EDUCATION("수학교육과")
    ,PUBLIC_ADMINISTRATION("행정학과")
    ,POLITICAL_SCIENCE_DIPLOMACY("정치외교학과")
    ,MEDIA_COMMUNICATION("미디어커뮤니케이션학과")
    ,ECONOMICS("경제학과")
    ,CONSUMER("소비자학과")
    ,CHILD_PSYCHOLOGY("아동심리학과")
    ,SOCIAL_WELFARE("사회복지학과")
    ,KOREAN_LITERATURE("한국어문학과")
    ,HISTORY("사학과")
    ,PHILOSOPHY("철학과")
    ,SINOLOGY("중국학과")
    ,JAPANESE_LANGUAGE_CULTURE("일본언어문화학과")
    ,ENGLISH_AMERICAN_LITERATURE("영어영문학과")
    ,FRANCE_LANGUAGE_CULTURE("프랑스언어문화학과")
    ,CULTURE_CONTENT_MANAGEMENT("문화콘텐츠문화경영학과")
    ,PREMEDICAL("의예과")
    ,NURSING_SCIENCE("간호학과")
    ,FORMATIVE_ARTS("조형예술학과")
    ,DESIGN_CONVERGENCE("디자인융합학과")
    ,SPORTS_SCIENCE("스포츠과학과")
    ,THEATER_FILM("연극영화학과")
    ,CLOTHING_DESIGN("의류디자인학과")
    ,ARTIFICIAL_INTELLIGENCE("인공지능공학과")
    ,DATA_SCIENCE("데이터사이언스학과")
    ,SMART_MOBILITY("스마트모빌리티공학과")
    ,DESIGN_TECHNOLOGY("디자인테크놀로지학과")
    ,COMPUTER("컴퓨터공학과")
    ,ARCHITECTURE("건축학전공")
    ,CONSTRUCTION_ENVIRONMENTAL("건설환경공학과")
    ,TRANSPORTATION_LOGISTICS("교통 물류공학과")
    ,MATERIALS_CHEMICAL("재료화학공학과")
    ,BIOLOGICAL_NANOTECHNOLOGY("생명나노공학과")
    ,ROBOTICS("로봇공학과")
    ,CONVERGENCE("융합공학과")
    ,DEFENSE_INFORMATION("국방정보공학과")
    ,SMART_CONVERGENCE("스마트융합공학부")
    ,INTELLIGENT_ROBOTICS("지능형로봇학과")
    ,KOREAN_LANGUAGE_LITERATURE("한국언어문학과")
    ,CULTURAL_ANTHROPOLOGY("문화인류학과")
    ,CULTURAL_CONTENTS("문화콘텐츠학과")
    ,JAPANESE("일본학과")
    ,AMERICAN_LANGUAGE_CULTURE("영미언어문화학과")
    ,FRENCH("프랑스학과")
    ,ADVERTISING_PUBLIC_RELATIONS("광고홍보학과")
    ,INFORMATION_SOCIAL_MEDIA("정보사회미디어학과")
    ,INSURANCE_ACCOUNTING("보험계리학과")
    ,ACCOUNTING_TAXATION("회계세무학과")
    ,BUSINESS_ANALYTIC_CONVERGENCE("비즈니스애널리틱스융합전공")
    ,ICT_CONVERGENCE("ICT융합학부")
    ,PHARMACY("약학과")
    ,MATHEMATICAL_DATA_SCIENCE("수리 데이터 사이언스학과")
    ,APPLIED_PHYSICS("응용물리학과")
    ,MEDICINE_LIFE_SCIENCES("의약생명과학과")
    ,CHEMICAL_MOLECULAR("화학분자공학과")
    ,OCEAN_CONVERGENCE("해양융합공학과")
    ,NANO_OPTOELECTRONICS("나노광전자학과")
    ,JEWELRY_FASHION_DIAGRAM("주얼리패션디자인학과")
    ,INDUSTRIAL_DESIGN("산업디자인학과")
    ,COMMUNICATION_DESIGN("커뮤니케이션디자인학과")
    ,IMAGE_DESIGN("영상디자인학과")
    ,SPORTS_CULTURE("스포츠문화전공")
    ,SPORTS_COACHING("스포츠코칭전공")
    ,DANCE_ARTS("무용예술학과")
    ,PRACTICAL_MUSIC("실용음악학과")
    ,CREATIVE_CONVERGENCE_EDUCATION("창의융합교육")
    ,NATIONAL_HISTORY("국사학과")
    ,ENGLISH_LITERATURE("영미영문학부")
    ,CHINESE_LANGUAGE_CULTURE("중국언어문화학과")
    ,JAPANESE_DEPARTMENT_CULTURE("일어일본문화학과")
    ,FRENCH_LANGUAGE_CULTURE("프랑스어문화학과")
    ,MUSIC("음악과")
    ,RELIGIOUS_STUDIES("종교학과")
    ,THEOLOGICAL("신학과")
    ,PSYCHOLOGY("심리학과")
    ,SOCIOLOGY("사회학과")
    ,SPECIAL_EDUCATION("특수교육과")
    ,ACCOUNTING("회계학과")
    ,INTERNATIONAL_STUDIES("국제학부")
    ,LAW("법학과")
    ,GLOBAL_FUTURE_MANAGEMENT("글로벌미래경영학과")
    ,TAX_ACCOUNTING_FINANCE("세무회계금융학과")
    ,IT_FINANCE("IT파이낸스학과")
    ,SPATIAL_DESIGN_CONSUMER_AFFAIRS("공간디자인소비자학과")
    ,CLOTHING("의류학과")
    ,CHILDREN_STUDIES("아동학과")
    ,MEDICAL_LIFE_SCIENCES("의생명과학과")
    ,NURSING("간호대학")
    ,MEDICAL("의과대학")
    ,COMPUTER_INFORMATION("컴퓨터정보공학부")
    ,MEDIA_TECHNOLOGY_CONTENT("미디어기술콘텐츠학과")
    ,INFORMATION_COMMUNICATION_ELECTRONICS("정보통신전자공학부")
    ,ENERGY_ENVIRONMENTAL("에너지환경공학과")
    ,BIOMEDICAL_ENVIRONMENTAL("바이오메디컬화학공학과")
    ,BIOMEDICAL_SOFTWARE("바이오메디컬소프트웨어학과")
    ,FREE_MAJOR("자유전공학과")
    ,TEACHING("교직과")
    ,GAME_ENGINEERING("게임공학과")
    ,SOFTWARE("소프트웨어전공")
    ,MECHANICAL_DESIGN("기계설계공학과")
    ,MECHATRONICS("메카트로닉스전공")
    ,AI_ROBOT("AI로봇전공")
    ,EMBEDDED_SYSTEMS("임베디드시스템전공")
    ,NEW_MATERIALS("신소재공학과")
    ,BIOCHEMICAL("생명화학공학과")
    ,NANO_SEMICONDUCTOR("나노반도체공학과")
    ,ENERGY_ELECTRICAL("에너지전기공학과")
    ,IT_MANAGEMENT("IT경영전공")
    ,DATA_SCIENCE_MANAGEMENT("데이터사이언스경영전공")
    ,MEDIA_DESIGN("미디어디자인공학전공")
    ,KNOWLEDGE_CONVERGENCE("지식융합학부")
    ;
    private final String value;

    @Override
    public String getKey() {return name();}
}