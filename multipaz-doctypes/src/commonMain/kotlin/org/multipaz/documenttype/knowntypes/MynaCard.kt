package org.multipaz.documenttype.knowntypes

import kotlinx.datetime.LocalDate
import org.multipaz.cbor.buildCborMap
import org.multipaz.cbor.toDataItem
import org.multipaz.cbor.toDataItemFullDate
import org.multipaz.documenttype.DocumentAttributeType
import org.multipaz.documenttype.DocumentType
import org.multipaz.documenttype.Icon
import org.multipaz.util.fromBase64Url

/**
 * PhotoID according to ISO/IEC TS 23220-4 (E) operational phase - Annex C Photo ID v2
 * 2024-08-14" (WG4/N4583)
 */
object MynaCard {
    const val MYNA_CARD_DOCTYPE = "org.iso.23220.1.jp.mnc"
    const val MYNA_CARD_NAMESPACE = "org.iso.23220.1.jp"
    const val ISO_23220_2_NAMESPACE = "org.iso.23220.1"

    /**
     * Build the Driving License Document Type.
     */
    fun getDocumentType(): DocumentType {
        return DocumentType.Builder("マイナンバーカード")
            .addMdocDocumentType(MYNA_CARD_DOCTYPE)
            // First the data elements from ISO/IEC 23220-2.
            //
            .addMdocAttribute(
                DocumentAttributeType.String,
                "full_name_unicode",
                "氏名",
                "氏名",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.PERSON,
                "山田　太郎".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "resident_address_unicode",
                "住所",
                "住所",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.PLACE,
                "〇県□□市△町◇丁目○番地▽▽号".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "local_gov_code_unicode",
                "市町村コード",
                "市町村コード",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.NUMBERS,
                "12345".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "individual_number_unicode",
                "個人番号",
                "個人番号",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.NUMBERS,
                "123456789012".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.Picture,
                "portrait",
                "証明写真",
                "証明写真",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.ACCOUNT_BOX,
                SampleData.PORTRAIT_BASE64URL.fromBase64Url().toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "sex_unicode",
                "性別",
                "性別",
                false,
                MYNA_CARD_NAMESPACE,
                Icon.EMERGENCY,
                "女".toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "sex",
                "性別",
                "性別",
                false,
                ISO_23220_2_NAMESPACE,
                Icon.EMERGENCY,
                SampleData.SEX_ISO218.toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.Boolean,
                "age_over_20",
                "20歳以上",
                "Indication whether the document holder is as old or older than 20",
                false,
                ISO_23220_2_NAMESPACE,
                Icon.TODAY,
                SampleData.AGE_OVER.toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.Number,
                "age_in_years",
                "年齢",
                "The age of the document holder",
                false,
                ISO_23220_2_NAMESPACE,
                Icon.TODAY,
                SampleData.AGE_IN_YEARS.toDataItem()
            )
            .addMdocAttribute(
                DocumentAttributeType.Date,   // TODO: this is a more complex type
                "birth_date",
                "Date of Birth",
                "Day, month and year on which the document holder was born. If unknown, approximate date of birth",
                true,
                ISO_23220_2_NAMESPACE,
                Icon.TODAY,
                buildCborMap {
                    put("birth_date", LocalDate.parse(SampleData.BIRTH_DATE).toDataItemFullDate())
                }
            )
            .addMdocAttribute(
                DocumentAttributeType.String,
                "birth_date_unicode",
                "生年月日",
                "生年月日",
                true,
                MYNA_CARD_NAMESPACE,
                Icon.TODAY,
                "2000年3月31日".toDataItem()
            )
            .addSampleRequest(
                id = "full",
                displayName ="全情報",
                mdocDataElements = mapOf(
                    MYNA_CARD_NAMESPACE to mapOf(),
                    ISO_23220_2_NAMESPACE to mapOf(),
                )
            )
            .addSampleRequest(
                id = "id",
                displayName ="氏名住所証明写真",
                mdocDataElements = mapOf(
                    MYNA_CARD_NAMESPACE to mapOf(
                        "full_name_unicode" to false,
                        "resident_address_unicode" to false,
                        "portrait" to false,
                    ),
                )
            )
            .addSampleRequest(
                id = "age_over_20",
                displayName ="20歳以上",
                mdocDataElements = mapOf(
                    ISO_23220_2_NAMESPACE to mapOf(
                        "age_over_20" to false
                    ),
                )
            )
            .addSampleRequest(
                id = "age_in_years",
                displayName ="年齢確認",
                mdocDataElements = mapOf(
                    ISO_23220_2_NAMESPACE to mapOf(
                        "age_in_years" to false
                    ),
                )
            )
            .build()
    }
}