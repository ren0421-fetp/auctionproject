<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Sign Up</title>

<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/app/cssPath/public-theme.css">
</head>
<body class="auction-public-page auth-page auth-page-register">
    <div class="page-orb orb-left"></div>
    <div class="page-orb orb-right"></div>
    <div class="page-orb orb-bottom"></div>

    <nav class="navbar navbar-expand-lg bg-transparent pt-4">
        <div class="container">
            <a class="navbar-brand brand-mark" href="${pageContext.request.contextPath}/app/home">
                Online Auction
            </a>

            <div class="d-flex gap-2">
                <a class="btn btn-light border rounded-pill px-4 py-2 nav-action"
                   href="${pageContext.request.contextPath}/app/login">
                    Sign In
                </a>
                <a class="btn btn-dark rounded-pill px-4 py-2 nav-action active-auth-link"
                   href="${pageContext.request.contextPath}/app/registration">
                    Sign Up
                </a>
            </div>
        </div>
    </nav>

    <main class="pt-3 pb-5">
        <div class="container">
            <section class="auth-shell auth-shell-register">
                <div class="row g-4 align-items-start">
                    <div class="col-lg-5">
                        <div class="auth-copy pe-lg-4 sticky-register-copy">
                            <span class="badge rounded-pill hero-badge px-4 py-2 mb-4">
                                Create Your Account
                            </span>

                            <h1 class="auth-title mb-4">
                                Join the platform and enter the
                                <span class="headline-accent">auction flow</span>
                            </h1>

                            <p class="auth-lead mb-4">
                                Register as a bidder or seller, complete your profile, and get ready to participate.
                            </p>

                            <div class="auth-feature-list">
                                <div class="auth-feature-card">
                                    <div class="auth-feature-title">Bidder Access</div>
                                    <div class="auth-feature-copy">Browse products, request packages, and place bids.</div>
                                </div>
                                <div class="auth-feature-card auth-feature-card-shift">
                                    <div class="auth-feature-title">Seller Access</div>
                                    <div class="auth-feature-copy">Publish products, review bidder activity, and manage listings.</div>
                                </div>
                                <div class="auth-feature-card">
                                    <div class="auth-feature-title">Profile Setup</div>
                                    <div class="auth-feature-copy">Add your details, location, and optional profile photo.</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-7">
                        <div class="auth-panel auth-panel-register">
                            <div class="auth-panel-header mb-4">
                                <div class="workspace-kicker">SIGN UP</div>
                                <h2 class="workspace-title mb-2">Create an account</h2>
                                <p class="preview-copy mb-0">
                                    Fill in your information below to register as a bidder or seller.
                                </p>
                            </div>

                            <form:form modelAttribute="registrationForm" method="post" enctype="multipart/form-data">

                                <div class="row g-3">
                                    <div class="col-md-6">
                                        <form:label path="firstName" cssClass="form-label auth-label">First Name</form:label>
                                        <form:input path="firstName" cssClass="form-control auth-input" />
                                        <form:errors path="firstName" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="lastName" cssClass="form-label auth-label">Last Name</form:label>
                                        <form:input path="lastName" cssClass="form-control auth-input" />
                                        <form:errors path="lastName" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-12">
                                        <label class="form-label auth-label">Gender</label>
                                        <div class="auth-radio-group">
                                            <c:forEach var="entry" items="${genderOpts}">
                                                <label class="auth-radio-pill">
                                                    <form:radiobutton path="gender" value="${entry.key}" />
                                                    <span>${entry.value}</span>
                                                </label>
                                            </c:forEach>
                                        </div>
                                        <form:errors path="gender" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-12">
                                        <form:label path="address" cssClass="form-label auth-label">Address</form:label>
                                        <form:textarea path="address" cssClass="form-control auth-input auth-textarea" rows="3" />
                                        <form:errors path="address" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-4">
                                        <form:label path="countryId" cssClass="form-label auth-label">Country</form:label>
                                        <form:select path="countryId" id="countryId" cssClass="form-select auth-input">
                                            <form:option value="" label="-- select country --"/>
                                            <form:options items="${countryOpts}" itemValue="countryId" itemLabel="countryName"/>
                                        </form:select>
                                        <form:errors path="countryId" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-4">
                                        <form:label path="stateId" cssClass="form-label auth-label">State</form:label>
                                        <form:select path="stateId" id="stateId" cssClass="form-select auth-input">
                                            <option value="">-- select state --</option>
                                            <c:forEach var="state" items="${stateOpts}">
                                                <option value="${state.stateId}"
                                                        data-country-id="${state.countryId}"
                                                        <c:if test="${registrationForm.stateId == state.stateId}">selected</c:if>>
                                                    <c:out value="${state.stateName}" />
                                                </option>
                                            </c:forEach>
                                        </form:select>
                                        <form:errors path="stateId" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-4">
                                        <form:label path="cityId" cssClass="form-label auth-label">City</form:label>
                                        <form:select path="cityId" id="cityId" cssClass="form-select auth-input">
                                            <option value="">-- select city --</option>
                                            <c:forEach var="city" items="${cityOpts}">
                                                <option value="${city.cityId}"
                                                        data-state-id="${city.stateId}"
                                                        <c:if test="${registrationForm.cityId == city.cityId}">selected</c:if>>
                                                    <c:out value="${city.cityName}" />
                                                </option>
                                            </c:forEach>
                                        </form:select>
                                        <form:errors path="cityId" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="username" cssClass="form-label auth-label">Username</form:label>
                                        <form:input path="username" cssClass="form-control auth-input" />
                                        <form:errors path="username" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="email" cssClass="form-label auth-label">Email</form:label>
                                        <form:input path="email" cssClass="form-control auth-input" />
                                        <form:errors path="email" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="password" cssClass="form-label auth-label">Password</form:label>
                                        <form:password path="password" cssClass="form-control auth-input" />
                                        <form:errors path="password" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="confirmPassword" cssClass="form-label auth-label">Confirm Password</form:label>
                                        <form:password path="confirmPassword" cssClass="form-control auth-input" />
                                        <form:errors path="confirmPassword" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="contactNo" cssClass="form-label auth-label">Contact Number</form:label>
                                        <form:input path="contactNo" cssClass="form-control auth-input" />
                                        <form:errors path="contactNo" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-md-6">
                                        <form:label path="userType" cssClass="form-label auth-label">Account Type</form:label>
                                        <form:select path="userType" cssClass="form-select auth-input">
                                            <form:option value="" label="-- select type --"/>
                                            <form:options items="${userTypeOpts}"/>
                                        </form:select>
                                        <form:errors path="userType" cssClass="auth-error" element="div" />
                                    </div>

                                    <div class="col-12">
                                        <form:label path="photoFile" cssClass="form-label auth-label">Profile Photo</form:label>
                                        <form:input path="photoFile" type="file" accept="image/*" cssClass="form-control auth-input auth-file-input" />
                                        <form:errors path="photoFile" cssClass="auth-error" element="div" />
                                    </div>
                                </div>

                                <div class="d-grid gap-3 mt-4">
                                    <button type="submit" class="btn btn-dark btn-lg rounded-pill auth-submit">
                                        Create Account
                                    </button>
                                </div>
                            </form:form>

                            <div class="auth-footer-note mt-4">
                                Already registered?
                                <a href="${pageContext.request.contextPath}/app/login" class="auth-link">
                                    Sign in here
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </div>
    </main>

    <script>
        const countrySelect = document.getElementById("countryId");
        const stateSelect = document.getElementById("stateId");
        const citySelect = document.getElementById("cityId");

        const allStateOptions = Array.from(stateSelect.querySelectorAll("option"))
            .slice(1)
            .map(option => option.cloneNode(true));

        const allCityOptions = Array.from(citySelect.querySelectorAll("option"))
            .slice(1)
            .map(option => option.cloneNode(true));

        function resetSelect(select, label) {
            select.innerHTML = "";
            const option = document.createElement("option");
            option.value = "";
            option.textContent = label;
            select.appendChild(option);
        }

        function populateStates(countryId, selectedStateId) {
            resetSelect(stateSelect, "-- select state --");

            allStateOptions
                .filter(option => countryId && option.dataset.countryId === countryId)
                .forEach(option => {
                    const clone = option.cloneNode(true);
                    clone.selected = clone.value === selectedStateId;
                    stateSelect.appendChild(clone);
                });
        }

        function populateCities(stateId, selectedCityId) {
            resetSelect(citySelect, "-- select city --");

            allCityOptions
                .filter(option => stateId && option.dataset.stateId === stateId)
                .forEach(option => {
                    const clone = option.cloneNode(true);
                    clone.selected = clone.value === selectedCityId;
                    citySelect.appendChild(clone);
                });
        }

        const initialCountryId = countrySelect.value;
        const initialStateId = stateSelect.value;
        const initialCityId = citySelect.value;

        populateStates(initialCountryId, initialStateId);
        populateCities(initialStateId, initialCityId);

        countrySelect.addEventListener("change", function () {
            populateStates(this.value, "");
            populateCities("", "");
        });

        stateSelect.addEventListener("change", function () {
            populateCities(this.value, "");
        });
    </script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
