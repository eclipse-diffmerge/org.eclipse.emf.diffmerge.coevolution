/**
 * <copyright>
 * 
 * Copyright (c) 2016 Thales Global Services S.A.S.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Thales Global Services S.A.S. - initial API and implementation
 * 
 * </copyright>
 */
package org.eclipse.emf.diffmerge.bridge.capella.integration.policies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.emf.diffmerge.api.scopes.IFeaturedModelScope;
import org.eclipse.emf.diffmerge.api.scopes.IModelScope;
import org.eclipse.emf.diffmerge.impl.policies.ConfigurableMatchPolicy;
import org.eclipse.emf.diffmerge.util.structures.comparable.ComparableTreeMap;
import org.eclipse.emf.diffmerge.util.structures.comparable.IComparableStructure;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.polarsys.capella.common.data.modellingcore.AbstractNamedElement;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.libraries.LibrariesPackage;
import org.polarsys.capella.common.libraries.LibraryReference;
import org.polarsys.capella.core.data.capellacommon.Region;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.Generalization;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.KeyValue;
import org.polarsys.capella.core.data.capellacore.ModellingArchitecture;
import org.polarsys.capella.core.data.capellamodeller.CapellamodellerPackage;
import org.polarsys.capella.core.data.capellamodeller.Library;
import org.polarsys.capella.core.data.capellamodeller.Project;
import org.polarsys.capella.core.data.cs.AbstractDeploymentLink;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentAllocation;
import org.polarsys.capella.core.data.cs.ComponentContext;
import org.polarsys.capella.core.data.cs.ExchangeItemAllocation;
import org.polarsys.capella.core.data.cs.InterfaceImplementation;
import org.polarsys.capella.core.data.cs.InterfaceUse;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalLinkEnd;
import org.polarsys.capella.core.data.ctx.CapabilityExploitation;
import org.polarsys.capella.core.data.ctx.CtxPackage;
import org.polarsys.capella.core.data.ctx.SystemAnalysis;
import org.polarsys.capella.core.data.epbs.EpbsPackage;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeEnd;
import org.polarsys.capella.core.data.fa.FunctionPkg;
import org.polarsys.capella.core.data.fa.FunctionRealization;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.communication.CommunicationLink;
import org.polarsys.capella.core.data.information.datatype.BooleanType;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.BinaryExpression;
import org.polarsys.capella.core.data.information.datavalue.LiteralBooleanValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;
import org.polarsys.capella.core.data.information.datavalue.ValuePart;
import org.polarsys.capella.core.data.interaction.AbstractCapabilityInclude;
import org.polarsys.capella.core.data.la.LaPackage;
import org.polarsys.capella.core.data.oa.OaPackage;
import org.polarsys.capella.core.data.pa.PaPackage;
import org.polarsys.capella.core.model.handler.helpers.CapellaProjectHelper;
import org.polarsys.capella.core.model.helpers.naming.NamingConstants;


/**
 * A multi-criteria match policy for Capella.
 * @author Olivier Constant
 */
public class CapellaConfigurableMatchPolicy extends ConfigurableMatchPolicy {
  
  /**
   * A criterion for finer-grained tuning of matching
   */
  public static class FineGrainedMatchCriterion {
    /** The non-null main criterion to which this criterion is relative */
    private final MatchCriterionKind _category;
    /** A non-null label for the criterion */
    private final String _label;
    /** An optional description for the criterion */
    private final String _description;
    /**
     * Constructor
     * @param category_p a non-null main criterion
     * @param label_p a non-null label
     * @param description_p a potentially null description
     */
    public FineGrainedMatchCriterion(MatchCriterionKind category_p, String label_p,
        String description_p) {
      _category = category_p;
      _label = label_p;
      _description = description_p;
    }
    /**
     * Return the main criterion to which this criterion is relative
     * @return a non-null object
     */
    public MatchCriterionKind getCategory() {
      return _category;
    }
    /**
     * Return the description of this criterion, if any
     * @return a potentially null object
     */
    public String getDescription() {
      return _description;
    }
    /**
     * Return the label of this criterion
     * @return a non-null object
     */
    public String getLabel() {
      return _label;
    }
  }

  
  /** A criterion for structural matching of roots */
  public static final FineGrainedMatchCriterion CRITERION_STRUCTURE_ROOTS =
      new FineGrainedMatchCriterion(MatchCriterionKind.STRUCTURE,
          Messages.ConfigurableMatchPolicy_Structure_Roots_Name,
          Messages.ConfigurableMatchPolicy_Structure_Roots_Description);
  
  /** A criterion for structural matching by containments */
  public static final FineGrainedMatchCriterion CRITERION_STRUCTURE_CONTAINMENTS =
      new FineGrainedMatchCriterion(MatchCriterionKind.STRUCTURE,
          Messages.ConfigurableMatchPolicy_Structure_Children_Name,
          Messages.ConfigurableMatchPolicy_Structure_Children_Description);
  
  /** A criterion for structural matching by containments */
  public static final FineGrainedMatchCriterion CRITERION_STRUCTURE_UNIQUECHILDREN =
      new FineGrainedMatchCriterion(MatchCriterionKind.STRUCTURE,
          Messages.ConfigurableMatchPolicy_Structure_UniqueChildren_Name,
          Messages.ConfigurableMatchPolicy_Structure_UniqueChildren_Description);

  /** A criterion for semantic matching of project structure */
  public static final FineGrainedMatchCriterion CRITERION_SEMANTICS_DEFAULTCONTENTS =
      new FineGrainedMatchCriterion(MatchCriterionKind.SEMANTICS,
          Messages.ConfigurableMatchPolicy_Semantics_DefaultContents_Name,
          Messages.ConfigurableMatchPolicy_Semantics_DefaultContents_Description);
  
  /** A criterion for semantic matching of technical elements */
  public static final FineGrainedMatchCriterion CRITERION_SEMANTICS_TECHNICALELEMENTS =
      new FineGrainedMatchCriterion(MatchCriterionKind.SEMANTICS,
          Messages.DecoratedCapellaComparisonMethodFactory_TechnicalElements_Name,
          Messages.DecoratedCapellaComparisonMethodFactory_TechnicalElements_Description);
  
  /** A criterion for name-based matching of exchanges and links */
  public static final FineGrainedMatchCriterion CRITERION_QNAMES_EXCHANGES =
      new FineGrainedMatchCriterion(MatchCriterionKind.NAME,
          Messages.DecoratedCapellaComparisonMethodFactory_Links_Name,
          Messages.DecoratedCapellaComparisonMethodFactory_Links_Description);
  
  /** The string identifying the Capella project approach configuration */
  public static final String CAPELLA_PROJECT_APPROACH = "projectApproach"; //$NON-NLS-1$
  
  /** The strings identifying the Capella progress status literals */
  @SuppressWarnings("nls")
  protected static final Collection<String> CAPELLA_PROGRESS_STATUS_LITERALS = Arrays.asList(
      "DRAFT", "TO_BE_REVIEWED", "TO_BE_DISCUSSED", "REWORK_NECESSARY", "UNDER_REWORK", "REVIEWED_OK");
  
  /** The strings identifying the Capella predefined types */
  protected static final Collection<String> CAPELLA_PREDEFINED_TYPE_NAMES = Arrays.asList(
      NamingConstants.PredefinedTypesCmd_boolean_name,
      NamingConstants.PredefinedTypesCmd_byte_name,
      NamingConstants.PredefinedTypesCmd_char_name,
      NamingConstants.PredefinedTypesCmd_double_name,
      NamingConstants.PredefinedTypesCmd_float_name,
      NamingConstants.PredefinedTypesCmd_hexadecimal_name,
      NamingConstants.PredefinedTypesCmd_integer_name,
      NamingConstants.PredefinedTypesCmd_long_name,
      NamingConstants.PredefinedTypesCmd_longLong_name,
      NamingConstants.PredefinedTypesCmd_short_name,
      NamingConstants.PredefinedTypesCmd_string_name,
      NamingConstants.PredefinedTypesCmd_unsignedInteger_name,
      NamingConstants.PredefinedTypesCmd_unsignedLong_name,
      NamingConstants.PredefinedTypesCmd_unsignedLongLong_name,
      NamingConstants.PredefinedTypesCmd_unsignedShort_name
      );
  
  /** The strings identifying the Capella predefined boolean literals */
  protected static final Collection<String> CAPELLA_PREDEFINED_BOOLEAN_LITERALS = Arrays.asList(
      NamingConstants.PredefinedTypesCmd_trueValue_name,
      NamingConstants.PredefinedTypesCmd_falseValue_name);
  
  /** A representation of the corresponding semantic property */
  protected static final String SEMANTIC_ID_TYPE_PROPERTY = "SEMANTIC_TYPE"; //$NON-NLS-1$
  /** A representation of the corresponding semantic property */
  protected static final String SEMANTIC_ID_END1_PROPERTY = "~END1"; //$NON-NLS-1$
  /** A representation of the corresponding semantic property */
  protected static final String SEMANTIC_ID_END2_PROPERTY = "~END2"; //$NON-NLS-1$
  
  /**
   * The set of Capella containment references which are discriminating w.r.t. their children
   * even though they are "isMany"
   */
  private static Collection<EReference> __DISCRIMINATING_CONTAINMENTS = null;
  
  /**
   * The set of Capella types which may normally have no more than one instance per model
   * or library.
   */
  private static Collection<EClass> __UNIQUELY_OCCURRING_TYPES = null;

  /** The set of fine-grained match criteria to use */
  private final Set<FineGrainedMatchCriterion> _selectedFineGrainedCriteria;
  
  /**
   * Default constructor
   */
  public CapellaConfigurableMatchPolicy() {
    super();
    _selectedFineGrainedCriteria = new HashSet<FineGrainedMatchCriterion>();
  }

  /**
   * Set whether the given fine-grained match criterion must be used
   * @param criterion_p a non-null criterion
   * @param use_p whether it must be used
   */
  public void setUseFineGrainedMatchCriterion(FineGrainedMatchCriterion criterion_p, boolean use_p) {
    if (use_p)
      _selectedFineGrainedCriteria.add(criterion_p);
    else
      _selectedFineGrainedCriteria.remove(criterion_p);
  }

  /**
   * Return whether the given fine-grained match criterion is selected for being used
   * by this match policy, independently of the fact that its category is used or not
   * @param criterion_p a non-null criterion
   */
  public boolean useFineGrainedMatchCriterion(FineGrainedMatchCriterion criterion_p) {
    return _selectedFineGrainedCriteria.contains(criterion_p);
  }

  /**
   * Return the set of applicable match criteria in decreasing priority
   * @return a non-null collection
   */
  @Override
  public Collection<MatchCriterionKind> getApplicableCriteria() {
    return Arrays.asList(MatchCriterionKind.values());
  }
  
  /**
   * Return the set of default match criteria among the applicable ones
   * @return a non-null collection
   */
  @Override
  public Collection<MatchCriterionKind> getDefaultCriteria() {
    return Arrays.asList(
        MatchCriterionKind.SEMANTICS,
        MatchCriterionKind.STRUCTURE);
  }
  
  /**
   * Return the set of available fine-grained match criteria, independently of the
   * fact that their category is applicable or not
   * @return a non-null, modifiable list
   */
  public List<FineGrainedMatchCriterion> getAvailableFineGrainedCriteria() {
    List<FineGrainedMatchCriterion> result = new ArrayList<FineGrainedMatchCriterion>();
    result.add(CRITERION_SEMANTICS_DEFAULTCONTENTS);
    result.add(CRITERION_SEMANTICS_TECHNICALELEMENTS);
    result.add(CRITERION_STRUCTURE_ROOTS);
    result.add(CRITERION_STRUCTURE_UNIQUECHILDREN);
    result.add(CRITERION_STRUCTURE_CONTAINMENTS);
    result.add(CRITERION_QNAMES_EXCHANGES);
    return result;
  }

  /**
   * @see org.eclipse.emf.diffmerge.impl.policies.DefaultMatchPolicy#getMatchID(org.eclipse.emf.ecore.EObject, org.eclipse.emf.diffmerge.api.scopes.IModelScope)
   */
  @Override
  public IComparableStructure<?> getMatchID(EObject element_p, IModelScope scope_p) {
    IComparableStructure<?> result = null;
    Iterator<MatchCriterionKind> it = getApplicableCriteria().iterator();
    while (result == null && it.hasNext()) {
      MatchCriterionKind criterion = it.next();
      if (useMatchCriterion(criterion))
        result = getMatchID(element_p, scope_p, criterion);
    }
    return result;
  }
  
  /**
   * Return a match ID for the given element from the given scope according
   * to the given criterion
   * @param element_p a non-null element
   * @param scope_p a non-null scope
   * @param criterion_p a non-null criterion
   * @return a potentially null object
   */
  @Override
  public IComparableStructure<?> getMatchID(EObject element_p, IModelScope scope_p,
      MatchCriterionKind criterion_p) {
    IComparableStructure<?> result;
    switch (criterion_p) {
    case EXTRINSIC_ID:
      result = getEncapsulateOrNull(getExtrinsicID(element_p, scope_p)); break;
    case INTRINSIC_ID:
      result = getEncapsulateOrNull(getIntrinsicID(element_p)); break;
    case NAME:
      result = getEncapsulateOrNull(getQualifiedName(element_p, scope_p)); break;
    case STRUCTURE:
      result = getEncapsulateOrNull(getStructureBasedID(element_p, scope_p)); break;
    default:
      result = getEncapsulateOrNull(getSemanticID(element_p, scope_p)); break;
    }
    return result;
  }
  
  /**
   * Return a match ID for the given element from the given scope
   * based on the role played by the element in the model structure
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers the element
   * @return a potentially null object
   */
  protected String getStructureBasedID(EObject element_p, IModelScope scope_p) {
    String result = null;
    EReference containment = getContainment(element_p, scope_p);
    if (containment == null && useFineGrainedMatchCriterion(CRITERION_STRUCTURE_ROOTS)) {
      result = getStructureBasedRootQualifier(element_p, scope_p);
    } else if (containment != null &&
        (useFineGrainedMatchCriterion(CRITERION_STRUCTURE_CONTAINMENTS) ||
            useFineGrainedMatchCriterion(CRITERION_STRUCTURE_UNIQUECHILDREN))) {
      result = getStructureBasedContainmentID(element_p, scope_p,
          !useFineGrainedMatchCriterion(CRITERION_STRUCTURE_CONTAINMENTS));
    }
    return result;
  }
  
  /**
   * Return a structural match ID for the given element based on its containment
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @param checkContainment_p whether the containment reference must be checked for its discriminating nature
   * @return a potentially null object
   */
  protected String getStructureBasedContainmentID(EObject element_p, IModelScope scope_p,
      boolean checkContainment_p) {
    String result = null;
    String lastIDPart = getStructureBasedContainmentQualifier(element_p, scope_p, checkContainment_p);
    if (isSignificant(lastIDPart))
      result = getContainerRelativeID(
          element_p, scope_p, lastIDPart, getQualificationSeparatorStructure());
    return result;
  }
  
  /**
   * Return a structural match ID qualifier for the given element based on its containment
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @param checkContainment_p whether the containment reference must be checked for its discriminating nature
   * @return a potentially null object
   */
  protected String getStructureBasedContainmentQualifier(EObject element_p, IModelScope scope_p,
      boolean checkContainment_p) {
    String result = null;
    EReference containment = getContainment(element_p, scope_p);
    if (containment != null &&
        (!checkContainment_p || isDiscriminatingContainment(element_p, containment)) &&
        isUniqueSiblingOfItsType(element_p, scope_p))
      result = containment.getName() + '[' + getStructuralTypeQualifier(element_p, scope_p) + ']';
    return result;
  }
  
  /**
   * Return the qualified name of the given element
   * @param element_p a non-null element
   * @param scope_p a non-null scope
   * @return a potentially null string
   */
  protected String getQualifiedName(EObject element_p, IModelScope scope_p) {
    String result = null;
    String name = getUniqueName(element_p, scope_p);
    if (isSignificant(name))
      result = getContainerRelativeID(
          element_p, scope_p, name, getQualificationSeparatorNames());
    return result;
  }
 
  /**
   * Return the name of the given element, unique within the container if possible
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers the element
   * @return a potentially null object
   */
  protected String getUniqueName(EObject element_p, IModelScope scope_p) {
    String result = null;
    if (element_p instanceof ENamedElement)
      result = ((ENamedElement)element_p).getName();
    return result;
  }
 
  /**
   * Return whether the given string can be considered as significant for contributing to
   * the identification of an element
   * @param string_p a potentially null string
   */
  protected boolean isSignificant(String string_p) {
    return string_p != null && string_p.length() > 0;
  }
  
  /**
   * Return an ID for the Capella model root of the given element, if applicable.
   * If the element is a root, then null is returned because the "default content"
   * criterion is not applicable.
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null object
   */
  protected String getCapellaDefaultContentRootSemanticID(EObject element_p, IModelScope scope_p) {
    String result = null;
    EObject root = EcoreUtil.getRootContainer(element_p);
    if (root != null && root != element_p)
      result = getStructureBasedRootQualifier(root, scope_p);
    return result;
  }
  
  /**
   * Return an ID based on the semantics of the given element as part of the
   * Capella Project structure, if applicable
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null object 
   */
  protected String getCapellaDefaultContentSemanticID(
      EObject element_p, IModelScope scope_p) {
    String result = null;
    final String rootPrefix =
        getCapellaDefaultContentRootSemanticID(element_p, scope_p);
    if (rootPrefix != null) {
      EClass type = element_p.eClass();
      if (getUniquelyOccurringTypes().contains(type)) {
        // Unique instance of its structure-related type in the model
        result = rootPrefix + getQualificationSeparatorDefault() + type.getName();
      } else {
        // Possibly multiple instances of the type
        if (isUniqueModelingArchitectureChild(element_p, scope_p) ||
            isSystemComponentAllocation(element_p, scope_p) ||
            isRootFunctionRealization(element_p, scope_p) ||
            isSystemStateMachine(element_p, scope_p) ||
            isSystemStateMachineMainRegion(element_p, scope_p)) {
          // Child of architecture, System component allocation,
          // Root function realization, System state machine & main region
          result = getContainerRelativeID(element_p, scope_p, type.getName(), null);
        } else if (isPredefinedTypePackage(element_p, scope_p) ||
            isPredefinedType(element_p, scope_p) ||
            isPredefinedBooleanLiteral(element_p, scope_p) ||
            isProgressStatusLiteral(element_p, scope_p)) {
          // Predefined Types package, Predefined types & literals,
          // Progress status enumeration literals
          result = getContainerRelativeID(
              element_p, scope_p, ((AbstractNamedElement)element_p).getName(), null);
        } else if (isSystemComponentPart(element_p, scope_p)) {
          // Part for System in ComponentContext in all architectures
          result = getContainerRelativeID(element_p, scope_p, "SystemPart", null); //$NON-NLS-1$
        } else if (isRootFunction(element_p, scope_p)) {
          // Root function
          result = getContainerRelativeID(element_p, scope_p, "Root" + type.getName(), null); //$NON-NLS-1$
        } else if (isPredefinedTypeProperty(element_p, scope_p)) {
          // Property of predefined type
          result = getContainerRelativeID(
              element_p, scope_p, getContainment(element_p, scope_p).getName(), null);
        } else if (isProjectApproach(element_p, scope_p)) {
          // Project approach key-value
          result = rootPrefix + getQualificationSeparatorSemantics() +
              ((KeyValue)element_p).getKey();
        } else if (isProgressStatus(element_p, scope_p)) {
          // Progress status enumeration type
          result = rootPrefix + getQualificationSeparatorSemantics() +
              CapellaProjectHelper.PROGRESS_STATUS_KEYWORD;
        }
      }
    }
    return result;
  }
  
  /**
   * Return a match ID for the given element from the given scope
   * based on the ID of its container and the given ID suffix
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers the element
   * @param qualifier_p a non-null suffix for the ID that identifies the element within its container
   * @param separator_p an optional string to use as qualification separator
   * @return a potentially null object
   */
  protected String getContainerRelativeID(EObject element_p,
      IModelScope scope_p, String qualifier_p, String separator_p) {
    String result = null;
    EObject container = getContainer(element_p, scope_p);
    if (container != null) {
      IComparableStructure<?> containerID = getMatchID(container, scope_p);
      if (containerID != null) {
        String separator = separator_p == null? getQualificationSeparatorDefault():
          separator_p;
        result = containerID + separator + qualifier_p;
      }
    } else {
      // Root
      result = qualifier_p;
    }
    return result;
  }
  
  /**
   * Return the container of the given element within the given scope
   * @param element_p a non-null element
   * @param scope_p a non-null scope
   * @return a potentially null reference
   */
  protected EObject getContainer(EObject element_p, IModelScope scope_p) {
    EObject result;
    if (isScopeOnly()) {
      if (scope_p instanceof IFeaturedModelScope)
        result = ((IFeaturedModelScope)scope_p).getContainer(element_p);
      else
        result = null;
    } else {
      result = element_p.eContainer();
    }
    return result;
  }
  
  /**
   * Return the set of Capella containment references which are discriminating w.r.t. their children
   * even though they are "isMany"
   * @return a non-null collection
   */
  protected Collection<EReference> getDiscriminatingContainments() {
    if (__DISCRIMINATING_CONTAINMENTS == null)
      __DISCRIMINATING_CONTAINMENTS = Arrays.asList(
          // Architecture allocations
          CtxPackage.eINSTANCE.getSystemAnalysis_OwnedOperationalAnalysisRealizations(),
          LaPackage.eINSTANCE.getLogicalArchitecture_OwnedSystemAnalysisRealizations(),
          PaPackage.eINSTANCE.getPhysicalArchitecture_OwnedLogicalArchitectureRealizations(),
          EpbsPackage.eINSTANCE.getEPBSArchitecture_OwnedPhysicalArchitectureRealizations()
          );
    return __DISCRIMINATING_CONTAINMENTS;
  }
  
  /**
   * Return a unique name for the given named element which has no naming requirement
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null string
   */
  protected String getFreelyNamedElementUniqueName(AbstractNamedElement element_p,
      IModelScope scope_p) {
    String result = null;
    if (useFineGrainedMatchCriterion(CRITERION_QNAMES_EXCHANGES)) {
      if (element_p instanceof FunctionalExchange) {
        // Functional Exchange
        FunctionalExchange casted = (FunctionalExchange)element_p;
        result = getTwoEndedElementUniqueName(
            casted, casted.getSourceFunctionOutputPort(), casted.getTargetFunctionInputPort(),
            scope_p);
      } else if (element_p instanceof ComponentExchange) {
        // Component Exchange
        ComponentExchange casted = (ComponentExchange)element_p;
        result = getTwoEndedElementUniqueName(
            casted, casted.getSourcePort(), casted.getTargetPort(), scope_p);
      } else if (element_p instanceof PhysicalLink) {
        // Physical Link
        PhysicalLink casted = (PhysicalLink)element_p;
        result = getTwoEndedElementUniqueName(
            casted, casted.getSourcePhysicalPort(), casted.getTargetPhysicalPort(), scope_p);
      }
    }
    return result;
  }
  
  /**
   * Return whether the given element is required to have a unique name, and if so
   * whether it is within its container or solely among its containment siblings
   * @param element_p a non-null element
   * @return a non-null object
   */
  protected UniqueNameRequirementKind getNamingRequirement(AbstractNamedElement element_p) {
    UniqueNameRequirementKind result;
    if (element_p instanceof FunctionalExchange ||
        element_p instanceof ComponentExchange ||
        element_p instanceof PhysicalLink ||
        isTechnical(element_p)) {
      result = UniqueNameRequirementKind.NONE;
    } else if (element_p instanceof Part) {
      result = UniqueNameRequirementKind.IN_CONTAINMENT;
    } else {
      result = UniqueNameRequirementKind.IN_CONTAINER;
    }
    return result;
  }

  /**
   * Unique name requirement kind enumeration 
   */
  protected static enum UniqueNameRequirementKind {
	  /** no requirement to be unique */
	  NONE,
	  /** unique within its container */
	  IN_CONTAINER,
	  /** unique among its containment siblings */
	  IN_CONTAINMENT
  }

  /**
   * Return a semantic ID for the given element according to the additional two
   * given elements
   * @param element_p a non-null element
   * @param end1_p a potentially null element
   * @param end2_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null object
   */
  protected String getTwoEndedElementSemanticID(
      EObject element_p, EObject end1_p, EObject end2_p, IModelScope scope_p) {
    String result = null;
    if (end1_p != null && end2_p != null) {
      IComparableStructure<?> id1 = getMatchID(end1_p, scope_p);
      if (id1 != null) {
    	IComparableStructure<?> id2 = getMatchID(end2_p, scope_p);
        if (id2 != null) {
          IComparableStructure<?> typeID = getEncapsulateOrNull(element_p.eClass().getName());
          Map<String, IComparableStructure<?>> map = new ComparableTreeMap<String, IComparableStructure<?>>();
          map.put(SEMANTIC_ID_TYPE_PROPERTY, typeID);
          map.put(SEMANTIC_ID_END1_PROPERTY, id1);
          map.put(SEMANTIC_ID_END2_PROPERTY, id2);
          result = map.toString();
        }
      }
    }
    return result;
  }
  
  /**
   * Return a name for the given element which is characterized by the additional two
   * given elements
   * @param element_p a non-null element
   * @param end1_p a potentially null element
   * @param end2_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null string
   */
  protected String getTwoEndedElementUniqueName(AbstractNamedElement element_p,
      EObject end1_p, EObject end2_p, IModelScope scope_p) {
    String result = null;
    if (end1_p != null && end2_p != null) {
      IComparableStructure<?> qname1 = getMatchID(end1_p, scope_p);
      if (qname1 != null) {
    	IComparableStructure<?> qname2 = getMatchID(end2_p, scope_p);
        if (qname2 != null) {
          StringBuilder builder = new StringBuilder();
          String mainName = element_p.getName();
          builder.append(mainName);
          builder.append(" ("); //$NON-NLS-1$
          builder.append(qname1);
          builder.append("->"); //$NON-NLS-1$
          builder.append(qname2);
          builder.append(')');
          result = builder.toString();
        }
      }
    }
    return result;
  }
  
  /**
   * Return a semantic-based match ID for the given element from the given scope
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers the element
   * @return a potentially null object
   */
  protected String getSemanticID(EObject element_p, IModelScope scope_p) {
    String result = null;
    if (useFineGrainedMatchCriterion(CRITERION_SEMANTICS_TECHNICALELEMENTS) &&
        isTechnical(element_p)) {
      result = getTechnicalElementSemanticID(element_p, scope_p);
    } else if (useFineGrainedMatchCriterion(CRITERION_SEMANTICS_DEFAULTCONTENTS)) {
      result = getCapellaDefaultContentSemanticID(element_p, scope_p);
    }
    return result;
  }

  /**
   * Return a structural match ID qualifier for the given root element
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null object
   */
  protected String getStructureBasedRootQualifier(EObject element_p, IModelScope scope_p) {
    String result = null;
    if (isUniqueSiblingOfItsType(element_p, scope_p))
      result = getQualificationSeparatorStructure() + getStructuralTypeQualifier(element_p, scope_p);
    if (result == null && element_p instanceof Library) {
      StringBuilder builder = new StringBuilder();
      builder.append(getQualificationSeparatorStructure());
      builder.append("CapellaLibrary<"); //$NON-NLS-1$
      builder.append(((Library)element_p).getId());
      builder.append('>');
      result =  builder.toString();
    }
    return result;
  }
  
  /**
   * Return whether the given element is the only one of its type among its siblings
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isUniqueSiblingOfItsType(EObject element_p, IModelScope scope_p) {
    Collection<EObject> siblings = getSiblings(element_p, scope_p);
    return isUniqueOfItsTypeAmong(element_p, siblings, scope_p);
  }
  
  /**
   * Return the siblings of the given element from the given scope, including the element itself.
   * Siblings are defined as elements owned by the same setting if the element has a container,
   * as roots of the same scope/resource otherwise.
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected List<EObject> getSiblings(EObject element_p, IModelScope scope_p) {
    List<EObject> result;
    EReference containment = getContainment(element_p, scope_p);
    if (containment == null) {
      Resource resource = element_p.eResource();
      if (isScopeOnly() || resource == null)
        result = scope_p.getContents();
      else
        result = resource.getContents();
    } else if (scope_p instanceof IFeaturedModelScope) {
      EObject container = getContainer(element_p, scope_p);
      result = ((IFeaturedModelScope)scope_p).get(container, containment);
    } else {
      result = Collections.emptyList();
    }
    return Collections.unmodifiableList(result);
  }
  
  /**
   * Return the containment reference of the given element within the given scope
   * @param element_p a non-null element
   * @param scope_p a non-null scope
   * @return a potentially null reference
   */
  protected EReference getContainment(EObject element_p, IModelScope scope_p) {
    EReference result;
    if (isScopeOnly()) {
      if (scope_p instanceof IFeaturedModelScope)
        result = ((IFeaturedModelScope)scope_p).getContainment(element_p);
      else
        result = null;
    } else {
      result = element_p.eContainmentFeature();
    }
    return result;
  }
  
  /**
   * Return whether the given element is the only one of its type among those in the given collection
   * @param element_p a non-null element
   * @param collection_p a non-null, potentially empty collection
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isUniqueOfItsTypeAmong(EObject element_p,
      Collection<? extends EObject> collection_p, IModelScope scope_p) {
    Iterator<? extends EObject> it = collection_p.iterator();
    boolean result = false;
    Object type = getStructuralType(element_p, scope_p);
    if (type != null) {
      boolean isPresent = false, sameType = false;
      while (it.hasNext() && !sameType) {
        EObject root = it.next();
        if (root == element_p) {
          isPresent = true;
        } else {
          if (type.equals(getStructuralType(root, scope_p)))
            sameType = true;
        }
      }
      result = isPresent && !sameType;
    }
    return result;
  }
  
  /**
   * Return a qualifier that characterizes the structural type of the given element
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a non-null string
   */
  protected String getStructuralTypeQualifier(EObject element_p, IModelScope scope_p) {
    Object type = getStructuralType(element_p, scope_p);
    String result;
    if (type instanceof EClass)
      result = ((EClass)type).getName();
    else
      result = type.toString();
    return result;
  }
  
  /**
   * Return an object that represents the type of the given element
   * for structure-based discrimination of elements.
   * Two elements have the same 'structural type' if and only if their types
   * are equal in the sense of {@link Object#equals(Object)}.
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a non-null object
   */
  protected Object getStructuralType(EObject element_p, IModelScope scope_p) {
    return element_p.eClass();
  }
  
  /**
   * Return an ID based on the semantics of the given technical element
   * Precondition: isTechnical(element_p)
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   * @return a potentially null object
   */
  protected String getTechnicalElementSemanticID(EObject element_p, IModelScope scope_p) {
    String result = null;
    if (element_p instanceof AbstractTrace) {
      // Abstract Trace
      AbstractTrace casted = (AbstractTrace)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getSourceElement(), casted.getTargetElement(), scope_p);
    } else if (element_p instanceof AbstractCapabilityInclude) {
      // Abstract Capability Include
      AbstractCapabilityInclude casted = (AbstractCapabilityInclude)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getInclusion(), casted.getIncluded(), scope_p);
    } else if (element_p instanceof AbstractDeploymentLink) {
      // Abstract Deployment Link
      AbstractDeploymentLink casted = (AbstractDeploymentLink)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getDeployedElement(), casted.getLocation(), scope_p);
    } else if (element_p instanceof CapabilityExploitation) {
      // Capability Exploitation
      CapabilityExploitation casted = (CapabilityExploitation)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getCapability(), casted.getMission(), scope_p);
    } else if (element_p instanceof CommunicationLink) {
      // Communication Link
      CommunicationLink casted = (CommunicationLink)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.eContainer(), casted.getExchangeItem(), scope_p);
    } else if (element_p instanceof ComponentExchangeEnd) {
      // Component Exchange End
      ComponentExchangeEnd casted = (ComponentExchangeEnd)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getPart(), casted.getPort(), scope_p);
    } else if (element_p instanceof ExchangeItemAllocation) {
      // Exchange Item Allocation
      ExchangeItemAllocation casted = (ExchangeItemAllocation)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getAllocatingInterface(), casted.getAllocatedItem(), scope_p);
    } else if (element_p instanceof Generalization) {
      // Generalization
      Generalization casted = (Generalization)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getSub(), casted.getSuper(), scope_p);
    } else if (element_p instanceof InterfaceImplementation) {
      // Interface Implementation
      InterfaceImplementation casted = (InterfaceImplementation)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getInterfaceImplementor(), casted.getImplementedInterface(), scope_p);
    } else if (element_p instanceof InterfaceUse) {
      // Interface Use
      InterfaceUse casted = (InterfaceUse)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getInterfaceUser(), casted.getUsedInterface(), scope_p);
    } else if (element_p instanceof Involvement) {
      // Involvement
      Involvement casted = (Involvement)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getInvolver(), casted.getInvolved(), scope_p);
    } else if (element_p instanceof ValuePart) {
      // Value Part
      Property property = ((ValuePart)element_p).getReferencedProperty();
      if (property != null)
        result = getContainerRelativeID(
            element_p, scope_p, property.getName(), null);
    } else if (element_p instanceof KeyValue) {
      // Key Value
      result = getContainerRelativeID(
          element_p, scope_p, ((KeyValue)element_p).getKey() + '|' + ((KeyValue)element_p).getValue(), null);
    } else if (element_p instanceof PhysicalLinkEnd) {
      // Physical Link End
      PhysicalLinkEnd casted = (PhysicalLinkEnd)element_p;
      result = getTwoEndedElementSemanticID(
          casted, casted.getPart(), casted.getPort(), scope_p);
    } else if (element_p instanceof LibraryReference) {
      // Library reference
      result = getContainerRelativeID(
          element_p, scope_p, ((LibraryReference)element_p).getLibrary().getId(), null);
    }
    return result;
  }
  
  /**
   * Return the set of Capella types which may normally have no more than one instance per model
   * or library
   * @return a non-null collection
   */
  protected Collection<EClass> getUniquelyOccurringTypes() {
    if (__UNIQUELY_OCCURRING_TYPES == null)
      __UNIQUELY_OCCURRING_TYPES = Arrays.asList(
          // Project roots
          CapellamodellerPackage.eINSTANCE.getProject(),
          CapellamodellerPackage.eINSTANCE.getSystemEngineering(),
          // Architectures
          OaPackage.eINSTANCE.getOperationalAnalysis(),
          CtxPackage.eINSTANCE.getSystemAnalysis(),
          LaPackage.eINSTANCE.getLogicalArchitecture(),
          PaPackage.eINSTANCE.getPhysicalArchitecture(),
          EpbsPackage.eINSTANCE.getEPBSArchitecture(),
          // Contexts
          OaPackage.eINSTANCE.getOperationalContext(),
          CtxPackage.eINSTANCE.getSystemContext(),
          LaPackage.eINSTANCE.getLogicalContext(),
          PaPackage.eINSTANCE.getPhysicalContext(),
          EpbsPackage.eINSTANCE.getEPBSContext(),
          // Libraries
          LibrariesPackage.eINSTANCE.getModelInformation()
          );
    return __UNIQUELY_OCCURRING_TYPES;
  }

  /**
   * @see org.eclipse.emf.diffmerge.impl.policies.ConfigurableMatchPolicy#isDiscriminatingContainment(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EReference)
   */
  @Override
  protected boolean isDiscriminatingContainment(EObject element_p, EReference containment_p) {
    return super.isDiscriminatingContainment(element_p, containment_p) ||
        getDiscriminatingContainments().contains(containment_p);
  }
  
  /**
   * Return whether the given element represents a Capella predefined type
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isPredefinedBooleanLiteral(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof LiteralBooleanValue) {
      EObject container = getContainer(element_p, scope_p);
      if (container instanceof BooleanType && isPredefinedType(container, scope_p))
        result = CAPELLA_PREDEFINED_BOOLEAN_LITERALS.contains(
            ((LiteralBooleanValue)element_p).getName());
    }
    return result;
  }
  
  /**
   * Return whether the given element represents a Capella predefined type
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isPredefinedType(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof DataType) {
      EObject container = getContainer(element_p, scope_p);
      if (isPredefinedTypePackage(container, scope_p))
        result = CAPELLA_PREDEFINED_TYPE_NAMES.contains(
            ((DataType)element_p).getName());
    }
    return result;
  }
  
  /**
   * Return whether the given element represents the package of predefined types
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isPredefinedTypePackage(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof DataPkg) {
      EObject container = getContainer(element_p, scope_p);
      if (container instanceof DataPkg) {
        EObject superContainer = getContainer(container, scope_p);
        if (superContainer instanceof SystemAnalysis)
          result = NamingConstants.PredefinedTypesCmd_predefinedDataTypePkg_name.equals(
              ((DataPkg)element_p).getName());
      }
    }
    return result;
  }
  
  /**
   * Return whether the given element is a property of a Capella predefined type
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isPredefinedTypeProperty(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof NumericValue) {
      EObject container = getContainer(element_p, scope_p);
      if (isPredefinedType(container, scope_p)) {
        // Numeric property directly owned by predefined type
        result = isInDiscriminatingContainment(element_p, scope_p);
      } else if (container instanceof BinaryExpression &&
          isInDiscriminatingContainment(container, scope_p)) {
        // Cases such as Hexadecimal::ownedMaxValue as a decomposed expression
        EObject typeCandidate = getContainer(container, scope_p);
        if (typeCandidate instanceof BinaryExpression &&
            isInDiscriminatingContainment(typeCandidate, scope_p))
          typeCandidate = getContainer(typeCandidate, scope_p); // Up to 3rd container
        result = isPredefinedType(typeCandidate, scope_p);
      }
    }
    return result;
  }
  
  /**
   * Return whether the given element is owned via a discriminating containment in the given scope
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isInDiscriminatingContainment(EObject element_p, IModelScope scope_p) {
    EReference containment = getContainment(element_p, scope_p);
    return containment != null && isDiscriminatingContainment(element_p, containment);
  }
  
  /**
   * Return whether the given element represents the Capella progress status type
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isProgressStatus(EObject element_p, IModelScope scope_p) {
    return element_p instanceof EnumerationPropertyType &&
        getContainer(element_p, scope_p) instanceof Project &&
        CapellaProjectHelper.PROGRESS_STATUS_KEYWORD.equals(
            ((EnumerationPropertyType)element_p).getName());
  }
  
  /**
   * Return whether the given element represents a Capella progress status literal
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isProgressStatusLiteral(EObject element_p, IModelScope scope_p) {
    return element_p instanceof EnumerationPropertyLiteral &&
        isProgressStatus(getContainer(element_p, scope_p), scope_p) &&
        CAPELLA_PROGRESS_STATUS_LITERALS.contains(
            ((EnumerationPropertyLiteral)element_p).getName());
  }
  
  /**
   * Return whether the given element represents the Capella project approach
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isProjectApproach(EObject element_p, IModelScope scope_p) {
    return element_p instanceof KeyValue &&
        getContainer(element_p, scope_p) instanceof Project &&
        CAPELLA_PROJECT_APPROACH.equals(((KeyValue)element_p).getKey());
  }
  
  /**
   * Return whether the given element represents the unique root function
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isRootFunction(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof AbstractFunction) {
      EObject container = getContainer(element_p, scope_p);
      if (container instanceof FunctionPkg)
        result = getContainer(container, scope_p) instanceof ModellingArchitecture &&
          isUniqueSiblingOfItsType(element_p, scope_p);
    }
    return result;
  }
  
  /**
   * Return whether the given element represents a root function realization
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isRootFunctionRealization(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof FunctionRealization) {
      FunctionRealization realization = (FunctionRealization)element_p;
      result = isRootFunction(realization.getAllocatedFunction(), scope_p) &&
          isRootFunction(realization.getAllocatingFunction(), scope_p);
    }
    return result;
  }
  
  /**
   * Return whether the given element represents the system
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isSystemComponent(EObject element_p, IModelScope scope_p) {
    return element_p instanceof Component &&
        getContainer(element_p, scope_p) instanceof ModellingArchitecture;
  }
  
  /**
   * Return whether the given element represents the system
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isSystemComponentAllocation(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof ComponentAllocation) {
      ComponentAllocation allocation = (ComponentAllocation)element_p;
      result = isSystemComponent(allocation.getAllocatedComponent(), scope_p) &&
          isSystemComponent(allocation.getAllocatingComponent(), scope_p);
    }
    return result;
  }

  /**
   * Return whether the given element is a part that represents the system
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isSystemComponentPart(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof Part &&
        getContainer(element_p, scope_p) instanceof ComponentContext) {
      Part part = (Part)element_p;
      result = isSystemComponent(part.getType(), scope_p);
    }
    return result;
  }
  
  /**
   * Return whether the given element represents the unique state machine of the system
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isSystemStateMachine(EObject element_p, IModelScope scope_p) {
    boolean result = false;
    if (element_p instanceof StateMachine) {
      EObject container = getContainer(element_p, scope_p);
      result = container instanceof org.polarsys.capella.core.data.ctx.System &&
          isSystemComponent(container, scope_p) && isUniqueSiblingOfItsType(element_p, scope_p);
    }
    return result;
  }
  
  /**
   * Return whether the given element represents the main region of the state machine of the system
   * @param element_p a potentially null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isSystemStateMachineMainRegion(EObject element_p, IModelScope scope_p) {
    return element_p instanceof Region &&
        isSystemStateMachine(getContainer(element_p, scope_p), scope_p) &&
        isUniqueSiblingOfItsType(element_p, scope_p);
  }
  
  /**
   * Return whether the given element is considered technical
   * @param element_p a non-null element
   */
  protected boolean isTechnical(EObject element_p) {
    return
        element_p instanceof AbstractTrace ||
        element_p instanceof AbstractCapabilityInclude ||
        element_p instanceof AbstractDeploymentLink ||
        element_p instanceof CapabilityExploitation ||
        element_p instanceof CommunicationLink ||
        element_p instanceof ComponentExchangeEnd ||
        element_p instanceof ExchangeItemAllocation ||
        element_p instanceof Generalization ||
        element_p instanceof InterfaceImplementation ||
        element_p instanceof InterfaceUse ||
        element_p instanceof Involvement ||
        element_p instanceof ValuePart ||
        element_p instanceof KeyValue ||
        element_p instanceof LibraryReference ||
        element_p instanceof PhysicalLinkEnd;
  }
  
  /**
   * Return whether the given element is directly owned by a modeling architecture
   * in such a way that it can be uniquely identified
   * @param element_p a non-null element
   * @param scope_p a non-null scope that covers element_p
   */
  protected boolean isUniqueModelingArchitectureChild(EObject element_p, IModelScope scope_p) {
    return getContainer(element_p, scope_p) instanceof ModellingArchitecture &&
        isInDiscriminatingContainment(element_p, scope_p);
  }
  
  /**
   * Return the symbol to use by default for separating qualifiers in qualified IDs,
   * for example qualified names
   * @return a non-null string
   */
  protected String getQualificationSeparatorDefault() {
    return getQualificationSeparatorSemantics();
  }
  
  /**
   * Return the symbol to use by default for separating qualifiers in, e.g., qualified names
   * @return a non-null string
   */
  protected String getQualificationSeparatorNames() {
    return "::"; //$NON-NLS-1$
  }
  
  /**
   * Return the symbol to use by default for separating qualifiers in qualified IDs,
   * for example qualified names
   * @return a non-null string
   */
  protected String getQualificationSeparatorSemantics() {
    return "~"; //$NON-NLS-1$
  }
  
  /**
   * Return the symbol to use by default for separating qualifiers in, e.g., qualified names
   * @return a non-null string
   */
  protected String getQualificationSeparatorStructure() {
    return "@"; //$NON-NLS-1$
  }
}