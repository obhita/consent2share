package gov.samhsa.consent2share;

import org.hibernate.dialect.MySQL5InnoDBDialect;

public class ImprovedMySQLDialect extends MySQL5InnoDBDialect {
	    @Override
	    public boolean dropConstraints() {
	        // We don't need to drop constraints before dropping tables, that just leads to error
	        // messages about missing tables when we don't have a schema in the database
	        return false;
	    }

}
