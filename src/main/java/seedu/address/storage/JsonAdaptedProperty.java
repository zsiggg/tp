package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.address.Address;
import seedu.address.model.characteristics.Characteristics;
import seedu.address.model.person.Name;
import seedu.address.model.property.Description;
import seedu.address.model.property.Price;
import seedu.address.model.property.Property;
import seedu.address.model.property.PropertyName;


/**
 * Jackson-friendly version of {@link Property}.
 */
class JsonAdaptedProperty {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Property's %s field is missing!";

    private final String name;
    private final String price;
    private final String address;
    private final String description;
    // characteristics cannot be null; converted to "" for saving to storage if null
    private final String characteristics;
    private final String seller; // TODO: change to JsonAdaptedSeller


    /**
     * Constructs a {@code JsonAdaptedProperty} with the given property details.
     */
    @JsonCreator
    public JsonAdaptedProperty(@JsonProperty("name") String name, @JsonProperty("price") String price,
                             @JsonProperty("address") String address, @JsonProperty("description") String description,
                             @JsonProperty("characteristics") String characteristics,
                             @JsonProperty("seller") String seller) {
        this.name = name;
        this.price = price;
        this.address = address;
        this.description = description;
        this.characteristics = characteristics;
        this.seller = seller;
    }

    /**
     * Converts a given {@code Property} into this class for Jackson use.
     */
    public JsonAdaptedProperty(Property source) {
        name = source.getName().fullName;
        price = source.getPrice().value;
        address = source.getAddress().value;
        description = source.getDescription().value;
        characteristics = source.getCharacteristics()
                .map(Characteristics::toString)
                .orElse("");
        seller = source.getSeller();
    }

    /**
     * Converts this Jackson-friendly adapted property object into the model's {@code Property} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted property.
     */
    public Property toModelType() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PropertyName.class.getSimpleName()));
        }
        if (!PropertyName.isValidPropertyName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final PropertyName modelName = new PropertyName(name);

        if (price == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Price.class.getSimpleName()));
        }
        if (!Price.isValidPrice(price)) {
            throw new IllegalValueException(Price.MESSAGE_CONSTRAINTS);
        }
        final Price modelPrice = new Price(price);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (description == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Description.class.getSimpleName()));
        }
        if (!Description.isValidDescription(description)) {
            throw new IllegalValueException(Description.MESSAGE_CONSTRAINTS);
        }
        final Description modelDescription = new Description(name);

        if (characteristics == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Characteristics.class.getSimpleName()));
        }
        if (!characteristics.isEmpty()
                && !Characteristics.isValidCharacteristics(characteristics)) {
            throw new IllegalValueException(Characteristics.MESSAGE_CONSTRAINTS);
        }
        final Characteristics modelCharacteristics = characteristics.isEmpty()
                ? null
                : new Characteristics(characteristics);

        if (seller == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "seller"));
        }
        // TODO: implement after Seller class is implemented
        // if (!Seller.isValidSeller(description)) {
        //     throw new IllegalValueException(Seller.MESSAGE_CONSTRAINTS);
        // }
        final String modelSeller = seller;


        return new Property(modelName, modelPrice, modelAddress, modelDescription,
                modelSeller, modelCharacteristics);
    }
}
