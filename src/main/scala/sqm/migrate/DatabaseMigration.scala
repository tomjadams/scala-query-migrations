package sqm.migrate

import com.novocode.squery.combinator.Table

object Version extends Table[(Int, Long, String)]("db_version") {
  def id = column[Int]("id", O.AutoInc, O.NotNull)
  def version = column[Long]("version",  O.NotNull)
  def description = column[String]("description")
  def * = id ~ version ~ description
}

trait DatabaseMigration {
  import com.novocode.squery.session.SessionFactory

  /** The version of this migration. */
  def version: Long

  /** An optional description of the migration. */
  def description: Option[String] = None

  /**
   * Applies the changes in this migration by moving the database version of to this version.
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def up(implicit sf: SessionFactory): Unit

  /**
   * Rolls back the changes in this migration unapplying the changes in up.
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def down(implicit sf: SessionFactory): Unit

  final def upWithTransaction(implicit sf: SessionFactory) {
    sf.withTransaction {
      up(sf)
    }
  }

  final def downWithTransaction(implicit sf: SessionFactory) {
    sf.withTransaction {
      down(sf)
    }
  }
}
