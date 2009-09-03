package sqm.migrate

import com.novocode.squery.session.SessionFactory

final class MigrationRunner(sessionFactory: SessionFactory, log: { def info(message: => String): Unit }, migrations: List[DatabaseMigration]) {
  def migrateTo(version: Long) = {
    log.info("Migrating database to " + version)
    // TODO Log the name of each migration as its being run
  }
}
