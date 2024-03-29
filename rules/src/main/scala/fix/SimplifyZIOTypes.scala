package fix

import scalafix.v1._
import scala.meta._

class SimplifyZIOTypes extends SemanticRule("SimplifyZIOTypes") {
  override def fix(implicit doc: SemanticDocument): Patch =
    doc.tree.collect {
      case tree @ t"ZIO[$r, $e, $a]"    => Patch.replaceTree(tree, simplifyZIO(r, e, a))
      case tree @ t"IO[$e, $a]"         => Patch.replaceTree(tree, simplifyZIO(t"Any", e, a))
      case tree @ t"ZLayer[$r, $e, $a]" => Patch.replaceTree(tree, simplifyZLayer(r, e, a))
      case tree @ t"Layer[$r, $a]"      => Patch.replaceTree(tree, simplifyZLayer(t"Any", r, a))
    }.asPatch

  private def simplifyZIO(r: Type, e: Type, a: Type): String = (r, e) match {
    case (t"Any", t"Throwable") => s"Task[$a]"
    case (t"Any", t"Nothing")   => s"UIO[$a]"
    case (t"Any", e)            => s"IO[$e, $a]"
    case (_, t"Throwable")      => s"RIO[$r, $a]"
    case (_, t"Nothing")        => s"URIO[$r, $a]"
    case _                      => s"ZIO[$r, $e, $a]"
  }

  private def simplifyZLayer(r: Type, e: Type, a: Type): String = (r, e) match {
    case (t"Any", t"Throwable") => s"TaskLayer[$a]"
    case (t"Any", t"Nothing")   => s"ULayer[$a]"
    case (t"Any", e)            => s"Layer[$e, $a]"
    case (_, t"Throwable")      => s"RLayer[$r, $a]"
    case (_, t"Nothing")        => s"URLayer[$r, $a]"
    case _                      => s"ZLayer[$r, $e, $a]"
  }

}
