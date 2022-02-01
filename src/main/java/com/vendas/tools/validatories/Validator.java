package com.vendas.tools.validatories;

public interface Validator {
	<T> void valid(T entitie, T request);
}
