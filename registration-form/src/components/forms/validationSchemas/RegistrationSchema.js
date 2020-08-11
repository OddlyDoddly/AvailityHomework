import * as Yup from 'yup';

const RegistrationSchema = Yup.object().shape({
    firstName: Yup.string()
        .min(2, 'First name must be longer than 2 characters')
        .max(30, 'First name must be shorter than 30 characters')
        .required('First name is a required field.'),

    lastName: Yup.string()
        .min(2, 'Last name must be longer than 2 characters')
        .max(30, 'Last name must be shorter than 30 characters')
        .required('Last name is a required field.'),

    email: Yup.string()
        .email('Entered email address is not valid.')
        .required('Please enter a valid email address.'),

    npiNumber: Yup.string()
        .min(8, "NPI Number must be exactly 8 characters")
        .max(8, "NPI Number must be exactly 8 characters")
        .required("NPI Number is a required Field"),

    businessAddress: Yup.string()
        .min(4, "Business address must be longer than 4 characters")
        .max(100, "business address must be less than 100 characters")
        .required("Business Address is a required field"),

    businessAddress2: Yup.string()
        .max(100, "business address must be less than 100 characters"),

    phone: Yup.string()
        .min(11, "Phone number must be exactly 11 characters in length.")
        .max(11, "Phone number must be exactly 11 characters in length")
        .required("Business Address is a required field"),
});

export default RegistrationSchema;