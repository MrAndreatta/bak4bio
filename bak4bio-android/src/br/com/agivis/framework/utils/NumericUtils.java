/*
 * Copyright (c) 2012, Agivis Sistemas LTDA. Todos os direitos reservados.
 *
 * Os Programas desta Aplicação (que incluem tanto o software quanto a sua
 * documentação) contém informações proprietárias da Agivis Sistemas LTDA; eles são
 * licenciados de acordo com um contrato de licença contendo restrições de uso e
 * confidencialidade, e são também protegidos pela Lei 9609/98 e 9/610/98,
 * respectivamente Lei do Software e Lei dos Direitos Autorais. Engenharia
 * reversa, descompilação e desmontagem dos programas são proibidos. Nenhuma
 * parte destes programas pode ser reproduzida ou transmitida de nenhuma forma e
 * por nenhum meio, eletrônico ou mecânico, por motivo algum, sem a permissão
 * escrita da Agivis Sistemas LTDA.
 */
package br.com.agivis.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Métodos úteis relacionados a números.
 * Por exemplo, arredondamento de números.
 * 
 * @author Time Agivis
 */
public class NumericUtils {
	
	private static final int DEFAULT_DECIMAL_DIGITS = 2;

	/**
	 * Trunca BigDecimal para 2 casas decimais
	 * @param originalValue
	 * @return
	 */
	public static BigDecimal truncate(BigDecimal originalValue) {
		return truncate(originalValue, DEFAULT_DECIMAL_DIGITS);
	}
	
	/**
	 * Trunca BigDecimal para quantas casas forem definidas
	 * @param originalValue
	 * @param decimalDigits
	 * @return
	 */
	public static BigDecimal truncate(BigDecimal originalValue, int decimalDigits) {
		if (originalValue == null) {
			return null;
		}
		
		return originalValue.setScale(decimalDigits, RoundingMode.DOWN);
	}
	
}
