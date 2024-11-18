package rainbowfriends.daramserverv2.global.member.entity

import jakarta.persistence.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import rainbowfriends.daramserverv2.global.member.enums.Roles

@Document(indexName = "members")
data class MemberElasticsearch(
    @Id
    val id: Long?,
    @Field(type = FieldType.Text)
    val name: String,
    @Field(type = FieldType.Integer)
    val grade: Int,
    @Field(type = FieldType.Integer)
    val classNum: Int,
    @Field(type = FieldType.Integer)
    val number: Int,
    @Field(type = FieldType.Integer)
    val floor: Int,
    @Field(type = FieldType.Integer)
    val room: Int,
    @Field(type = FieldType.Keyword)
    val role: Roles,
    @Field(type = FieldType.Boolean)
    val stay: Boolean
)