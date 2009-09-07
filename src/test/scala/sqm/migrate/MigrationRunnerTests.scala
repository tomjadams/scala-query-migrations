package sqm.migrate

import org.specs._
import _root_.com.novocode.squery.session._

object DoesNotLog {
  def info(message: => String) {println(message)}
}

object MigrationRunnerTests extends Specification("Migration runner tests") {
  import MigrationRunner._
  import _root_.java.sql.SQLException
  import com.novocode.squery.session.SessionFactory._
  import com.novocode.squery.simple.StaticQueryBase._

  implicit def rsToVersion(rs: PositionedResult) = rs.nextLong()

  "A system without a database" can {
    lazy val unmigratedDatabase = new DriverManagerSessionFactory("jdbc:h2:mem:empty", "org.h2.Driver")

    "is empty when no migrations are applied" in {
      migrate(unmigratedDatabase, DoesNotLog, Nil, 0)
      unmigratedDatabase.withSession {
        val allVersions = queryNA[Long]("select version from db_version")
        allVersions.first must throwA[SQLException]
      }
    }

    "create a versioning table by applying a migration" in {
      migrate(unmigratedDatabase, DoesNotLog, List(CreateVersionTableMigration), 0)
      unmigratedDatabase.withSession {
        val allVersions = queryNA[Long]("select version from db_version")
        allVersions.first must be equalTo 0
      }
    }

    "be reset by applying a rollback" in {
      "TODO" must be equalTo "TODO"
    }
  }
}
