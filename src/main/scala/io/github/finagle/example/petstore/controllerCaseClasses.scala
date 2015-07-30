package io.github.finagle.example.petstore

import com.twitter.finatra.request.{RouteParam, QueryParam}

case class Id( @RouteParam id: Option[Long] )

case class SeekStatus( @QueryParam status: String )

case class SeekTags( @QueryParam tags: String )