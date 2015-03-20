package msse_auctions

import org.apache.commons.lang.builder.HashCodeBuilder

class AccountRole implements Serializable {

	private static final long serialVersionUID = 1

	Account account
	Role role

	boolean equals(other) {
		if (!(other instanceof AccountRole)) {
			return false
		}

		other.account?.id == account?.id &&
		other.role?.id == role?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (account) builder.append(account.id)
		if (role) builder.append(role.id)
		builder.toHashCode()
	}

	static AccountRole get(long accountId, long roleId) {
		AccountRole.where {
			account == Account.load(accountId) &&
			role == Role.load(roleId)
		}.get()
	}

	static boolean exists(long accountId, long roleId) {
		AccountRole.where {
			account == Account.load(accountId) &&
			role == Role.load(roleId)
		}.count() > 0
	}

	static AccountRole create(Account account, Role role, boolean flush = false) {
		def instance = new AccountRole(account: account, role: role)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(Account u, Role r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = AccountRole.where {
			account == Account.load(u.id) &&
			role == Role.load(r.id)
		}.deleteAll()

		if (flush) { AccountRole.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(Account u, boolean flush = false) {
		if (u == null) return

		AccountRole.where {
			account == Account.load(u.id)
		}.deleteAll()

		if (flush) { AccountRole.withSession { it.flush() } }
	}

	static void removeAll(Role r, boolean flush = false) {
		if (r == null) return

		AccountRole.where {
			role == Role.load(r.id)
		}.deleteAll()

		if (flush) { AccountRole.withSession { it.flush() } }
	}

	static constraints = {
		role validator: { Role r, AccountRole ur ->
			if (ur.account == null) return
			boolean existing = false
			AccountRole.withNewSession {
				existing = AccountRole.exists(ur.account.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['role', 'account']
		version false
	}
}
