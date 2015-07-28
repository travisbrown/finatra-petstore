package io.github.finagle.example.petstore

import com.google.inject.Singleton
import com.twitter.finagle.http.{Response, Request}
import com.twitter.finatra.http.HttpServer
import com.twitter.finatra.http.exceptions.ExceptionMapper
import com.twitter.finatra.http.filters.{ExceptionMappingFilter, CommonFilters}
import com.twitter.finatra.http.routing.HttpRouter
import com.twitter.finatra.json.modules.FinatraJacksonModule
import com.twitter.finatra.json.utils.CamelCasePropertyNamingStrategy
import com.twitter.finatra.logging.filter.{TraceIdMDCFilter, LoggingMDCFilter}
import com.twitter.finatra.logging.modules.LogbackModule
import com.twitter.inject.TwitterModule
import com.twitter.util.Await

object PetstoreServerMain extends PetstoreServer

class PetstoreServer extends HttpServer {

  //BEGIN SAMPLE PETSTORE=========================================================================

  val db = new PetstoreDb()
  val rover = Pet(None, "Rover", Nil, Some(Category(None, "dog")), Some(Seq(Tag(None, "puppy"), Tag(None, "white"))),
    Some(Status.Available))
  val jack = Pet(None, "Jack", Nil, Some(Category(None, "dog")), Some(Seq(Tag(None, "puppy"))), Some(Status.Available))
  val sue = Pet(None, "Sue", Nil, Some(Category(None, "dog")), Some(Nil), Some(Status.Adopted))
  val sadaharu = Pet(None, "Sadaharu", Nil, Some(Category(None, "inugami")), Some(Nil), Some(Status.Available))
  val despereaux = Pet(None, "Despereaux", Nil, Some(Category(None, "mouse")), Some(Nil), Some(Status.Pending))
  val alexander = Pet(None, "Alexander", Nil, Some(Category(None, "mouse")), Some(Nil), Some(Status.Pending))
  val wilbur = Pet(None, "Wilbur", Nil, Some(Category(None, "pig")), Some(Nil), Some(Status.Adopted))
  val cheshire = Pet(None, "Cheshire Cat", Nil, Some(Category(None, "cat")), Some(Nil), Some(Status.Available))
  val crookshanks = Pet(None, "Crookshanks", Nil, Some(Category(None, "cat")), Some(Nil), Some(Status.Available))
  val coraline: User = User(None, "coraline", Some("Coraline"), Some("Jones"), None, "becarefulwhatyouwishfor", None)
  Await.ready(db.addPet(rover))
  Await.ready(db.addPet(jack))
  Await.ready(db.addPet(sue))
  Await.ready(db.addPet(sadaharu))
  Await.ready(db.addPet(despereaux))
  Await.ready(db.addPet(alexander))
  Await.ready(db.addPet(wilbur))
  Await.ready(db.addPet(cheshire))
  Await.ready(db.addPet(crookshanks))
  Await.ready(db.addUser(coraline))

  //END SAMPLE PETSTORE=========================================================================

  override def modules = Seq(
    LogbackModule,
    new TwitterModule() {
      override protected def configure(): Unit = {
//        bind[PetstoreDb].in[Singleton]
        bind[PetstoreDb].toInstance(db) //comment out and switch with above when done
//        bind[PetstoreDb].in[Singleton]  //comment out and switch with above when done
      }
    }
  )

  override def jacksonModule = new FinatraJacksonModule {
    override val propertyNamingStrategy = CamelCasePropertyNamingStrategy
  }

  class PetstoreError extends Exception

  override def configureHttp(router: HttpRouter) {
    router
      .filter[LoggingMDCFilter[Request, Response]]
      .filter[TraceIdMDCFilter[Request, Response]]
      .filter[CommonFilters]
      .exceptionMapper[MissingPetMapper]
      .add[PetstorePetController]
  }
}
