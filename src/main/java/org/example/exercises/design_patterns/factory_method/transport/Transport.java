package org.example.exercises.design_patterns.factory_method.transport;
import org.example.exercises.design_patterns.factory_method.package_detail.Package;

public interface Transport  {
    TransportType deliver(Package aPackage);
}