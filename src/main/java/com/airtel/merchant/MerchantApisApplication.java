package com.airtel.merchant;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.airtel.merchant.dto.SettlementReportDTO;
import com.airtel.merchant.model.SettlementReport;

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class })
@SpringBootApplication
public class MerchantApisApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MerchantApisApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(MerchantApisApplication.class, args);
	}

	@Bean(name = "modelMapper")
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}

	@Bean(name = "customModelMapper")
	public ModelMapper modelMapperBean() {
		ModelMapper modelMapper = new ModelMapper();
		PropertyMap<SettlementReport, SettlementReportDTO> userToDTO = new PropertyMap<SettlementReport, SettlementReportDTO>() {
			protected void configure() {
				map().setNetCreditAmount(source.getNET_CREDIT_AMNT());
				map().setTransactionDate(source.getTXN_DATE());
				map().setTransactionId(source.getBANK_TXN_REF_NO());
				map().setPartnerTransactionId(source.getPARTNER_TXN_REF_NO());
				map().setCustomerMobileNumber(source.getCUSTOMER_MSISDN());
				map().setOrignalAmount(source.getORIG_AMNT());
				map().setStoreId(source.getSTORE_ID());
				map().setTillId(source.getTILL_ID());
				map().setUtr(source.getUTR_NUM());
			}
		};
		modelMapper.addMappings(userToDTO);
		return modelMapper;
	}
}
