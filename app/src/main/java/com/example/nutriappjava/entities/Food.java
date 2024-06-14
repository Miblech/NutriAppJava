package com.example.nutriappjava.entities;

import java.util.Objects;
public class Food {
    private Long id;
    private String category;
    private String description;
    private Integer nutrientDataBankNumber;
    private Integer alphaCarotene;
    private Integer betaCarotene;
    private Integer betaCryptoxanthin;
    private Float carbohydrate;
    private Integer cholesterol;
    private Float choline;
    private Float fiber;
    private Integer luteinAndZeaxanthin;
    private Integer lycopene;
    private Float niacin;
    private Float protein;
    private Integer retinol;
    private Float riboflavin;
    private Float selenium;
    private Float sugarTotal;
    private Float thiamin;
    private Float water;
    private Float monosaturatedFat;
    private Float polysaturatedFat;
    private Float saturatedFat;
    private Float totalLipid;
    private Integer calcium;
    private Float copper;
    private Float iron;
    private Integer magnesium;
    private Integer phosphorus;
    private Integer potassium;
    private Integer sodium;
    private Float zinc;
    private Integer vitaminARae;
    private Float vitaminB12;
    private Float vitaminB6;
    private Float vitaminC;
    private Float vitaminE;
    private Float vitaminK;

    // Constructors, getters, and setters
    public Food() {}

    public Food(Long id, String category, String description, Integer nutrientDataBankNumber, Integer alphaCarotene, Integer betaCarotene, Integer betaCryptoxanthin, Float carbohydrate, Integer cholesterol, Float choline, Float fiber, Integer luteinAndZeaxanthin, Integer lycopene, Float niacin, Float protein, Integer retinol, Float riboflavin, Float selenium, Float sugarTotal, Float thiamin, Float water, Float monosaturatedFat, Float polysaturatedFat, Float saturatedFat, Float totalLipid, Integer calcium, Float copper, Float iron, Integer magnesium, Integer phosphorus, Integer potassium, Integer sodium, Float zinc, Integer vitaminARae, Float vitaminB12, Float vitaminB6, Float vitaminC, Float vitaminE, Float vitaminK) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.nutrientDataBankNumber = nutrientDataBankNumber;
        this.alphaCarotene = alphaCarotene;
        this.betaCarotene = betaCarotene;
        this.betaCryptoxanthin = betaCryptoxanthin;
        this.carbohydrate = carbohydrate;
        this.cholesterol = cholesterol;
        this.choline = choline;
        this.fiber = fiber;
        this.luteinAndZeaxanthin = luteinAndZeaxanthin;
        this.lycopene = lycopene;
        this.niacin = niacin;
        this.protein = protein;
        this.retinol = retinol;
        this.riboflavin = riboflavin;
        this.selenium = selenium;
        this.sugarTotal = sugarTotal;
        this.thiamin = thiamin;
        this.water = water;
        this.monosaturatedFat = monosaturatedFat;
        this.polysaturatedFat = polysaturatedFat;
        this.saturatedFat = saturatedFat;
        this.totalLipid = totalLipid;
        this.calcium = calcium;
        this.copper = copper;
        this.iron = iron;
        this.magnesium = magnesium;
        this.phosphorus = phosphorus;
        this.potassium = potassium;
        this.sodium = sodium;
        this.zinc = zinc;
        this.vitaminARae = vitaminARae;
        this.vitaminB12 = vitaminB12;
        this.vitaminB6 = vitaminB6;
        this.vitaminC = vitaminC;
        this.vitaminE = vitaminE;
        this.vitaminK = vitaminK;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNutrientDataBankNumber() {
        return nutrientDataBankNumber;
    }

    public void setNutrientDataBankNumber(Integer nutrientDataBankNumber) {
        this.nutrientDataBankNumber = nutrientDataBankNumber;
    }

    public Integer getAlphaCarotene() {
        return alphaCarotene;
    }

    public void setAlphaCarotene(Integer alphaCarotene) {
        this.alphaCarotene = alphaCarotene;
    }

    public Integer getBetaCarotene() {
        return betaCarotene;
    }

    public void setBetaCarotene(Integer betaCarotene) {
        this.betaCarotene = betaCarotene;
    }

    public Integer getBetaCryptoxanthin() {
        return betaCryptoxanthin;
    }

    public void setBetaCryptoxanthin(Integer betaCryptoxanthin) {
        this.betaCryptoxanthin = betaCryptoxanthin;
    }

    public Float getCarbohydrate() {
        return carbohydrate;
    }

    public void setCarbohydrate(Float carbohydrate) {
        this.carbohydrate = carbohydrate;
    }

    public Integer getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(Integer cholesterol) {
        this.cholesterol = cholesterol;
    }

    public Float getCholine() {
        return choline;
    }

    public void setCholine(Float choline) {
        this.choline = choline;
    }

    public Float getFiber() {
        return fiber;
    }

    public void setFiber(Float fiber) {
        this.fiber = fiber;
    }

    public Integer getLuteinAndZeaxanthin() {
        return luteinAndZeaxanthin;
    }

    public void setLuteinAndZeaxanthin(Integer luteinAndZeaxanthin) {
        this.luteinAndZeaxanthin = luteinAndZeaxanthin;
    }

    public Integer getLycopene() {
        return lycopene;
    }

    public void setLycopene(Integer lycopene) {
        this.lycopene = lycopene;
    }

    public Float getNiacin() {
        return niacin;
    }

    public void setNiacin(Float niacin) {
        this.niacin = niacin;
    }

    public Float getProtein() {
        return protein;
    }

    public void setProtein(Float protein) {
        this.protein = protein;
    }

    public Integer getRetinol() {
        return retinol;
    }

    public void setRetinol(Integer retinol) {
        this.retinol = retinol;
    }

    public Float getRiboflavin() {
        return riboflavin;
    }

    public void setRiboflavin(Float riboflavin) {
        this.riboflavin = riboflavin;
    }

    public Float getSelenium() {
        return selenium;
    }

    public void setSelenium(Float selenium) {
        this.selenium = selenium;
    }

    public Float getSugarTotal() {
        return sugarTotal;
    }

    public void setSugarTotal(Float sugarTotal) {
        this.sugarTotal = sugarTotal;
    }

    public Float getThiamin() {
        return thiamin;
    }

    public void setThiamin(Float thiamin) {
        this.thiamin = thiamin;
    }

    public Float getWater() {
        return water;
    }

    public void setWater(Float water) {
        this.water = water;
    }

    public Float getMonosaturatedFat() {
        return monosaturatedFat;
    }

    public void setMonosaturatedFat(Float monosaturatedFat) {
        this.monosaturatedFat = monosaturatedFat;
    }

    public Float getPolysaturatedFat() {
        return polysaturatedFat;
    }

    public void setPolysaturatedFat(Float polysaturatedFat) {
        this.polysaturatedFat = polysaturatedFat;
    }

    public Float getSaturatedFat() {
        return saturatedFat;
    }

    public void setSaturatedFat(Float saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public Float getTotalLipid() {
        return totalLipid;
    }

    public void setTotalLipid(Float totalLipid) {
        this.totalLipid = totalLipid;
    }

    public Integer getCalcium() {
        return calcium;
    }

    public void setCalcium(Integer calcium) {
        this.calcium = calcium;
    }

    public Float getCopper() {
        return copper;
    }

    public void setCopper(Float copper) {
        this.copper = copper;
    }

    public Float getIron() {
        return iron;
    }

    public void setIron(Float iron) {
        this.iron = iron;
    }

    public Integer getMagnesium() {
        return magnesium;
    }

    public void setMagnesium(Integer magnesium) {
        this.magnesium = magnesium;
    }

    public Integer getPhosphorus() {
        return phosphorus;
    }

    public void setPhosphorus(Integer phosphorus) {
        this.phosphorus = phosphorus;
    }

    public Integer getPotassium() {
        return potassium;
    }

    public void setPotassium(Integer potassium) {
        this.potassium = potassium;
    }

    public Integer getSodium() {
        return sodium;
    }

    public void setSodium(Integer sodium) {
        this.sodium = sodium;
    }

    public Float getZinc() {
        return zinc;
    }

    public void setZinc(Float zinc) {
        this.zinc = zinc;
    }

    public Integer getVitaminARae() {
        return vitaminARae;
    }

    public void setVitaminARae(Integer vitaminARae) {
        this.vitaminARae = vitaminARae;
    }

    public Float getVitaminB12() {
        return vitaminB12;
    }

    public void setVitaminB12(Float vitaminB12) {
        this.vitaminB12 = vitaminB12;
    }

    public Float getVitaminB6() {
        return vitaminB6;
    }

    public void setVitaminB6(Float vitaminB6) {
        this.vitaminB6 = vitaminB6;
    }

    public Float getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(Float vitaminC) {
        this.vitaminC = vitaminC;
    }

    public Float getVitaminE() {
        return vitaminE;
    }

    public void setVitaminE(Float vitaminE) {
        this.vitaminE = vitaminE;
    }

    public Float getVitaminK() {
        return vitaminK;
    }

    public void setVitaminK(Float vitaminK) {
        this.vitaminK = vitaminK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(id, food.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ScaledFoodDTO toScaledFoodDTO(Float weight) {

        return new ScaledFoodDTO(
                this.category,
                this.description,
                this.nutrientDataBankNumber,
                this.alphaCarotene != null ? Math.round(this.alphaCarotene * weight / 100) : null,
                this.betaCarotene != null ? Math.round(this.betaCarotene * weight / 100) : null,
                this.betaCryptoxanthin != null ? Math.round(this.betaCryptoxanthin * weight / 100) : null,
                this.carbohydrate != null ? this.carbohydrate * weight / 100 : null,
                this.cholesterol != null ? Math.round(this.cholesterol * weight / 100) : null,
                this.choline != null ? this.choline * weight / 100 : null,
                this.fiber != null ? this.fiber * weight / 100 : null,
                this.luteinAndZeaxanthin != null ? Math.round(this.luteinAndZeaxanthin * weight / 100) : null,
                this.lycopene != null ? Math.round(this.lycopene * weight / 100) : null,
                this.niacin != null ? this.niacin * weight / 100 : null,
                this.protein != null ? this.protein * weight / 100 : null,
                this.retinol != null ? Math.round(this.retinol * weight / 100) : null,
                this.riboflavin != null ? this.riboflavin * weight / 100 : null,
                this.selenium != null ? this.selenium * weight / 100 : null,
                this.sugarTotal != null ? this.sugarTotal * weight / 100 : null,
                this.thiamin != null ? this.thiamin * weight / 100 : null,
                this.water != null ? this.water * weight / 100 : null,
                this.monosaturatedFat != null ? this.monosaturatedFat * weight / 100 : null,
                this.polysaturatedFat != null ? this.polysaturatedFat * weight / 100 : null,
                this.saturatedFat != null ? this.saturatedFat * weight / 100 : null,
                this.totalLipid != null ? this.totalLipid * weight / 100 : null,
                this.calcium != null ? Math.round(this.calcium * weight / 100) : null,
                this.copper != null ? this.copper * weight / 100 : null,
                this.iron != null ? this.iron * weight / 100 : null,
                this.magnesium != null ? Math.round(this.magnesium * weight / 100) : null,
                this.phosphorus != null ? Math.round(this.phosphorus * weight / 100) : null,
                this.potassium != null ? Math.round(this.potassium * weight / 100) : null,
                this.sodium != null ? Math.round(this.sodium * weight / 100) : null,
                this.zinc != null ? this.zinc * weight / 100 : null,
                this.vitaminARae != null ? Math.round(this.vitaminARae * weight / 100) : null,
                this.vitaminB12 != null ? this.vitaminB12 * weight / 100 : null,
                this.vitaminB6 != null ? this.vitaminB6 * weight / 100 : null,
                this.vitaminC != null ? this.vitaminC * weight / 100 : null,
                this.vitaminE != null ? this.vitaminE * weight / 100 : null,
                this.vitaminK != null ? this.vitaminK * weight / 100 : null
        );
    }
}
