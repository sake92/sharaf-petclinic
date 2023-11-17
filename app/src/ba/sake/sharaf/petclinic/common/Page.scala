package ba.sake.sharaf.petclinic.common

case class Page[T](
    items: Seq[T],
    number: Int,
    totalElements: Int
) {
  private val totalPages: Int = (totalElements / Page.size) + 1

  def map[A](function: T => A): Page[A] =
    val mappedItems = items.map(function)
    Page(mappedItems, number, totalElements)

  def hasItems: Boolean = items.nonEmpty

  def isFirst: Boolean = number == 0

  def isLast: Boolean = number == totalPages - 1

  def previousOrFirstPageable: PageRequest =
    val prevOrFirst = if isFirst then 0 else (number - 1)
    PageRequest(prevOrFirst)

  def nextOrLastPageable: PageRequest =
    val nextOrLast = if isLast then number else (number + 1)
    PageRequest(nextOrLast)

}

object Page {
  val size: Int = 10

  def empty[T] = Page[T](Seq.empty, 0, 0)
}
