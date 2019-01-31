import scala.concurrent.{ExecutionContext, Future}

object Main extends App {
  implicit val ec = ExecutionContext.global

  val one = Some(1)
  val two = Some(2)
  val three = Some(3)
  val four = Some(4)
  val five = Some(5)

  def sum(one: Option[Int], two: Option[Int], three: Option[Int], four: Option[Int], five: Option[Int])(implicit ec: ExecutionContext): Future[String] = {
    val result: Either[Future[String], Int] = for {
      d1 <- one.toRight(Future.successful("Error in one")).right
      d2 <- two.toRight(Future.successful("Error in two")).right
      d3 <- three.toRight(Future.successful("Error in three")).right
      d4 <- four.toRight(Future.successful("Error in four")).right
      d5 <- five.toRight(Future.successful("Error in five")).right
    } yield d1 + d2 + d3 + d4 + d5

    result.fold(error => error, sum => Future.successful(sum.toString))
  }

  def printFuture(futureString: Future[String]): Unit = futureString.map { println(_) }

  printFuture(sum(None, two, three, four, five))
  printFuture(sum(None, two, three, four, None))
  printFuture(sum(one, None, three, four, five))
  printFuture(sum(one, two, None, four, five))
  printFuture(sum(one, two, three, None, five))
  printFuture(sum(one, two, three, four, None))
  printFuture(sum(one, two, three, four, five))
}
