/**
 * Created by Emiliano Echinofora.
 */
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelper } from "angular2-jwt";
var AuthGuard = (function () {
    function AuthGuard(router) {
        this.router = router;
        this.jwtHelpter = new JwtHelper();
    }
    AuthGuard.prototype.canActivate = function () {
        /*      return true;*/
        if (!this.jwtHelpter.isTokenExpired(localStorage.getItem('token'))) {
            // logged in so return true
            return true;
        }
        // not logged in so redirect to login page
        this.router.navigate(['/login']);
        return false;
    };
    AuthGuard.prototype.canActivateChild = function () {
        /*      return true;*/
        return this.canActivate();
    };
    return AuthGuard;
}());
AuthGuard = __decorate([
    Injectable(),
    __metadata("design:paramtypes", [Router])
], AuthGuard);
export { AuthGuard };
//# sourceMappingURL=auth.guard.js.map
