package sqm.migrate

import com.novocode.squery.combinator.Implicit._
import com.novocode.squery.session.SessionFactory, SessionFactory._
import com.novocode.squery.simple.StaticQueryBase._

object DatabaseMigration0000 extends DatabaseMigration {
  override def version = 0
  override def description = Some("Create db_version table.")

  override def up(sf: SessionFactory) {
    Version.createTable
  }

  override def down(sf: SessionFactory) {
    updateNA("drop table db_version")
  }
}
