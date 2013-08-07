package gov.samhsa.ds4ppilot.orchestrator;

import java.util.UUID;

/**
 * The Class UniqueOidProviderImpl.
 */
public class UniqueOidProviderImpl implements UniqueOidProvider {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.samhsa.ds4ppilot.orchestrator.UniqueOidProvider#getOid()
	 */
	@Override
	public String getOid() {
		UUID uuid = UUID.randomUUID();
		String id = String.valueOf(uuid);

		id = id.replace("-", ".");

		id = id.replace("a", "10");
		id = id.replace("b", "11");
		id = id.replace("c", "12");
		id = id.replace("d", "13");
		id = id.replace("e", "14");
		id = id.replace("f", "15");

		// Removes leading zeroes
		id = id.replaceFirst("^0+(?!$)", "");

		return id;
	}

	/**
	 * The main method.
	 * 
	 * @param aArgs
	 *            the arguments
	 */
	public static final void main(String... aArgs) {
		UniqueOidProviderImpl provider = new UniqueOidProviderImpl();
		String oid = provider.getOid();

		System.out.println(oid);
	}
}
