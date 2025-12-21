package lmm.moneylog.data.misc

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 =
    object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // SQLite doesn't support ALTER COLUMN, so we need to:
            // 1. Create new table with correct schema
            // 2. Copy data from old table
            // 3. Drop old table
            // 4. Rename new table to old name

            // Create new transaction table with BIGINT id
            db.execSQL(
                """
                CREATE TABLE IF NOT EXISTS `transaction_new` (
                    `value` REAL NOT NULL,
                    `description` TEXT NOT NULL,
                    `day` INTEGER NOT NULL,
                    `month` INTEGER NOT NULL,
                    `year` INTEGER NOT NULL,
                    `paidDay` INTEGER,
                    `paidMonth` INTEGER,
                    `paidYear` INTEGER,
                    `invoiceMonth` INTEGER,
                    `invoiceYear` INTEGER,
                    `accountId` INTEGER,
                    `categoryId` INTEGER,
                    `creditCardId` INTEGER,
                    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    FOREIGN KEY(`accountId`) REFERENCES `account`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE,
                    FOREIGN KEY(`categoryId`) REFERENCES `category`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL,
                    FOREIGN KEY(`creditCardId`) REFERENCES `creditcard`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL
                )
                """.trimIndent()
            )

            // Create indices for new table
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_transaction_new_accountId` " +
                    "ON `transaction_new` (`accountId`)"
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_transaction_new_categoryId` " +
                    "ON `transaction_new` (`categoryId`)"
            )
            db.execSQL(
                "CREATE INDEX IF NOT EXISTS `index_transaction_new_creditCardId` " +
                    "ON `transaction_new` (`creditCardId`)"
            )

            // Copy data from old table to new table
            db.execSQL(
                """
                INSERT INTO `transaction_new` (
                    `id`, `value`, `description`, `day`, `month`, `year`,
                    `paidDay`, `paidMonth`, `paidYear`, `invoiceMonth`, `invoiceYear`,
                    `accountId`, `categoryId`, `creditCardId`
                )
                SELECT 
                    `id`, `value`, `description`, `day`, `month`, `year`,
                    `paidDay`, `paidMonth`, `paidYear`, `invoiceMonth`, `invoiceYear`,
                    `accountId`, `categoryId`, `creditCardId`
                FROM `transaction`
                """.trimIndent()
            )

            // Drop old table
            db.execSQL("DROP TABLE `transaction`")

            // Rename new table to old name
            db.execSQL("ALTER TABLE `transaction_new` RENAME TO `transaction`")
        }
    }
