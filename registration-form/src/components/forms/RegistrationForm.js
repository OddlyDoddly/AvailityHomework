import React, {Component} from 'react';
import { Formik } from "formik";
import PhoneInput from 'react-phone-input-2';
import 'react-phone-input-2/lib/style.css'

import './validationSchemas/RegistrationSchema';
import RegistrationSchema from "./validationSchemas/RegistrationSchema";

import {Row, Container, Col, Form} from 'react-bootstrap';

class RegistrationForm extends Component {

    constructor(props) {
        super(props);

        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(values) {
        console.log(values);
        alert("Form has been submitted, values have been logged to the console.")
    }

    createFormGroup(fieldName, placeHolder, label, onChange, onBlur, value, errors, maxLength=-1) {
        return (
            <Form.Group size="lg">
                <Row>
                    <Form.Label column sm="2" htmlFor={fieldName}>{label}: </Form.Label>
                    <Col sm="10">
                        <Form.Control
                            type={"text"}
                            name={fieldName}
                            placeholder={placeHolder}
                            onChange={onChange}
                            onBlur={onBlur}
                            value={value}
                            maxLength={maxLength}
                        />
                        <small className="form-text text-muted">
                            {errors[fieldName]}
                        </small>
                    </Col>
                </Row>
            </Form.Group>
            );
    }

    render() {
        return(
            <Formik
                initialValues={{
                    firstName: "",
                    lastName: "",
                    npiNumber: "",
                    businessAddress: "",
                    businessAddress2: "",
                    phone: "",
                    email: ""
                }}
                validationSchema={RegistrationSchema}
                onSubmit={this.handleSubmit}
            >
                {({values,
                      errors,
                      touched,
                      handleChange,
                      handleBlur,
                      handleSubmit,
                      setFieldValue,
                      isValid,
                      setTouched,
                      isSubmitting }) => (
                    <Container>
                        <Form
                            onSubmit={handleSubmit}
                        >
                            <h1>Register for Availity</h1>
                            {/*FirstName*/}
                            {this.createFormGroup("firstName", "John", "First Name", handleChange, handleBlur, values.firstName, errors)}
                            {this.createFormGroup("lastName", "Doe", "Last Name", handleChange, handleBlur, values.lastName, errors)}
                            {this.createFormGroup("email", "JohnDoe@example.com", "Email", handleChange, handleBlur, values.email, errors)}
                            <Form.Group size="lg">
                                <Row>
                                    <Form.Label column sm="2" htmlFor={"npiNumber"}>"NPI Number": </Form.Label>
                                    <Col sm="10">
                                        <Form.Control
                                            type={"number"}
                                            name="npiNumber"
                                            placeholder="00000000"
                                            onChange={handleChange}
                                            onBlur={handleBlur}
                                            value={values.npiNumber}
                                            maxLength={8}
                                        />
                                        <small className="form-text text-muted">
                                            {errors.npiNumber}
                                        </small>
                                    </Col>
                                </Row>
                            </Form.Group>
                            {this.createFormGroup("businessAddress", "1234 RealStreet st", "Business Address", handleChange, handleBlur, values.businessAddress, errors)}
                            {this.createFormGroup("businessAddress2", "Apt 1234", "Business Address Line 2", handleChange, handleBlur, values.businessAddress2, errors)}

                            <Form.Group size="lg">
                                <Row>
                                    <Form.Label column sm="2" htmlFor="phone">Phone Number: </Form.Label>
                                    <Col sm="10">
                                        <PhoneInput
                                            country={'us'}
                                            onChange={(phone, country) => {
                                                console.log(touched);
                                                setFieldValue("phone", phone);
                                            }}
                                            onBlur={handleBlur}
                                            name="phone"
                                        />
                                        <small className="form-text text-muted">
                                            {errors.phone}
                                        </small>
                                    </Col>
                                </Row>
                            </Form.Group>

                            <button
                                type="submit"
                                disabled={!isValid}
                            >Submit</button>
                        </Form>
                    </Container>
                )}
            </Formik>

        );
    }
}

export default RegistrationForm;