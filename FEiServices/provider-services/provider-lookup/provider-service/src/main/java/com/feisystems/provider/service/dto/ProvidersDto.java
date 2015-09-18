package com.feisystems.provider.service.dto;

import java.util.List;
import java.util.Map;

/**
 * The Class ProvidersDto.
 */
public class ProvidersDto  {

	/** The provider beans. */
	private List<ProviderDto> providerBeans;

	/** The total number of providers. */
	private long totalNumberOfProviders;

	/** The total pages. */
	private int totalPages;

	/** The items per page. */
	private int itemsPerPage;

	/** The current page. */
	private int currentPage;

	/**
	 * Instantiates a new providers dto.
	 */
	public ProvidersDto() {
	}

	/**
	 * Instantiates a new providers dto.
	 *
	 * @param providerBeans
	 *            the provider beans
	 */
	public ProvidersDto(List<ProviderDto> providerBeans) {
		this.providerBeans = providerBeans;
	}

	/**
	 * Instantiates a new providers dto.
	 *
	 * @param pagedProviderBeans
	 *            the paged provider beans
	 */
	public ProvidersDto(Map<String, Object> pageResults) {
		this.providerBeans = (List<ProviderDto>) pageResults.get("results");
		this.currentPage = 	(int) pageResults.get("currentPage");
		this.itemsPerPage = 	(int) pageResults.get("itemsPerPage");
		this.totalPages = 	(int) pageResults.get("totalPages");
		this.totalNumberOfProviders = 	(long) pageResults.get("totalNumberOfProviders");
	}

	/**
	 * Gets the providers.
	 *
	 * @return the providers
	 */
	public List<ProviderDto> getProviders() {
		return providerBeans;
	}

	/**
	 * Sets the providers.
	 *
	 * @param providerBeans
	 *            the new providers
	 */
	public void setProviders(List<ProviderDto> providerBeans) {
		this.providerBeans = providerBeans;
	}

	/**
	 * Gets the total pages.
	 *
	 * @return the total pages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/**
	 * Sets the total pages.
	 *
	 * @param totalPages
	 *            the new total pages
	 */
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	/**
	 * Gets the total number of providers.
	 *
	 * @return the total number of providers
	 */
	public long getTotalNumberOfProviders() {
		return totalNumberOfProviders;
	}

	/**
	 * Sets the total number of providers.
	 *
	 * @param totalNumberOfProviders
	 *            the new total number of providers
	 */
	public void setTotalNumberOfProviders(long totalNumberOfProviders) {
		this.totalNumberOfProviders = totalNumberOfProviders;
	}

	/**
	 * Gets the items per page.
	 *
	 * @return the items per page
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * Sets the items per page.
	 *
	 * @param itemsPerPage
	 *            the new items per page
	 */
	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	/**
	 * Gets the current page.
	 *
	 * @return the current page
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Sets the current page.
	 *
	 * @param currentPage
	 *            the new current page
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}
