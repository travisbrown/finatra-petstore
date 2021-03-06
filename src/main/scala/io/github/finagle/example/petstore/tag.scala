package io.github.finagle.example.petstore

/**
 * Represents a Tag for pets. Tags cannot be passed with user-made, IDs.
 * @param id The unique, autogenerated ID of this Tag.
 * @param name The name of this Tag.
 */
case class Tag(id: Option[Long], name: String)

/**
 * Represents a Tag object for pets.
 */
object Tag {
  /**
   * Creates the codec for converting Tags from and to JSON.
   */
//  implicit val tagCodec: CodecJson[Tag] =
//    casecodec2(Tag.apply, Tag.unapply)("id", "name")
}
