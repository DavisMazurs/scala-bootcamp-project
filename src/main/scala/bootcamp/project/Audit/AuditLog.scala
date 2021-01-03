package bootcamp.project.audit

import java.sql.Timestamp

case class AuditLog(
  id:             Int,
  user_id:        Int,
  audit_event_id: Int,
  timestamp:      Timestamp,
  export_success: Int
)
