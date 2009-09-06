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
  def up: Unit

  /**
   * Rolls back the changes in this migration unapplying the changes made in up.
   * This method excutes within a transaction, so 
   * Requires an implicit SessionFactory to be defined in scope.
   */
  def down: Unit

  final def upWithTransaction(sf: SessionFactory) {
    sf.withTransaction { up }
  }

  final def downWithTransaction(sf: SessionFactory) {
    sf.withTransaction { down}
  }
}
