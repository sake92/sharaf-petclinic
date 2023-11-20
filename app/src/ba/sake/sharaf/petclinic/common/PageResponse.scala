package ba.sake.sharaf.petclinic.common

case class PageResponse[T](
    items: Seq[T],
    number: Int,
    totalElements: Int
) {
  val totalPages: Int =
    (totalElements.toDouble / Page.size).ceil.toInt

  def map[A](function: T => A): PageResponse[A] =
    val mappedItems = items.map(function)
    PageResponse(mappedItems, number, totalElements)

  def hasItems: Boolean = items.nonEmpty

  def isFirst: Boolean = number == 0

  def isLast: Boolean = number == totalPages - 1

  def prevPageReq: PageRequest =
    val prevOrFirst = if isFirst then 0 else (number - 1)
    PageRequest(prevOrFirst)

  def nextPageReq: PageRequest =
    val nextOrLast = if isLast then number else (number + 1)
    PageRequest(nextOrLast)

}

object Page {
  val size: Int = 5
}
