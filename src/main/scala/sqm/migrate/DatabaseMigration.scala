package sqm.migrate

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
  def up(sf: SessionFactory): Unit

  /**
   * Rolls back the changes in this migration unapplying the changes in up.
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def down(sf: SessionFactory): Unit

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
