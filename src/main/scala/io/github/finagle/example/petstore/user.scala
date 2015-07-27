package io.github.finagle.example.petstore

/**
 * Represents a User in the system, who can interact with the petstore and purchase
 * available [[Pet]] objects.
 * @param id Unique, autogenerated ID of the User
 * @param username (Required)
 * @param firstName
 * @param lastName
 * @param email
 * @param password (Required)
 * @param phone
 */
case class User(
    id: Option[Long],
    username: String,
    firstName: Option[String],
    lastName: Option[String],
    email: Option[String],
    password: String,
    phone: Option[String]
    )

/**
 * Companion object to the User class.
 */
object User{
//  /**
//   * Creates the encode/decode codec for the User object.
//   */
//  implicit val userCodec: CodecJson[User] =
//    casecodec7(User.apply, User.unapply)("id", "username", "firstName", "lastName", "email", "password", "phone")
}
