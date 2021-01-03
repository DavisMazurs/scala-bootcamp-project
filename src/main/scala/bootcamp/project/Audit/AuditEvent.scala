package bootcamp.project.audit

case class AuditEvent(
  id:             Int,
  name:           String,
  module_id:      Int,
  audit_user:     Int,
  audit_export:   Int
)
