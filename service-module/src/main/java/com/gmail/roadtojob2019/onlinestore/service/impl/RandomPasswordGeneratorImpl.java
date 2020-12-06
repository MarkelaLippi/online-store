package com.gmail.roadtojob2019.onlinestore.service.impl;

import com.gmail.roadtojob2019.onlinestore.service.RandomPasswordGenerator;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

@Service
public class RandomPasswordGeneratorImpl implements RandomPasswordGenerator {
    @Override
    public String generateRandomPassword() {
        PasswordGenerator gen = new PasswordGenerator();

        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = EnglishCharacterData.Special;
        CharacterRule specialCharRule = new CharacterRule(specialChars);
        specialCharRule.setNumberOfCharacters(2);

        String password = gen.generatePassword(8, lowerCaseRule,
                upperCaseRule, digitRule, specialCharRule);
        return password;
    }
}
