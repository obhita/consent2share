package com.feisystems.provider.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.feisystems.provider.Provider;
import com.feisystems.provider.dtos.ProviderDto;

public class ProviderServiceTestBase {

	public ProviderServiceTestBase() {
		super();
	}

	
	protected List<Provider> postalCodeReturn(){
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Provider bean1 = new Provider("1111", "Individual", formatter.format(d),formatter.format(d));
		Provider bean2 = new Provider("2222", "Organization", formatter.format(d),formatter.format(d));
		Provider bean3 = new Provider("3333", "Individual", formatter.format(d),formatter.format(d));
		ArrayList<Provider> l = new ArrayList<Provider>();
		l.add(bean1);
		l.add(bean2);
		l.add(bean3);
		return l;
	}

	protected List<Provider> cityStateReturn(){
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Provider bean1 = new Provider("1111", "Individual", formatter.format(d),formatter.format(d));
		Provider bean2 = new Provider("2222", "Organization", formatter.format(d),formatter.format(d));
		Provider bean3 = new Provider("3333", "Individual",formatter.format(d),formatter.format(d));
		Provider bean4 = new Provider("4444", "Individual", formatter.format(d),formatter.format(d));
		ArrayList<Provider> l = new ArrayList<Provider>();
		l.add(bean1);
		l.add(bean2);
		l.add(bean3);
		l.add(bean4);
		return l;
	}

	protected List<ProviderDto> cityStateDtoReturn(){
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		ProviderDto bean1 = new ProviderDto("1111", "Individual", d,d);
		ProviderDto bean2 = new ProviderDto("2222", "Individual", d,d);
		ProviderDto bean3 = new ProviderDto("3333", "Individual", d,d);
		ProviderDto bean4 = new ProviderDto("4444", "Individual", d,d);
		ArrayList<ProviderDto> l = new ArrayList<ProviderDto>();
		l.add(bean1);
		l.add(bean2);
		l.add(bean3);
		l.add(bean4);
		return l;
	}
}