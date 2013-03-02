class BlastsController < ApplicationController
  load_resource
  authorize_resource
  before_filter :load_contents, :except => [:index]
  
  # GET /blasts
  # GET /blasts.json
  def index
    @blasts = Blast.accessible_by(current_ability)
    
    if params[:filter] && !params[:filter].blank?
      @blasts = Blast.where("title LIKE ?", "%#{params[:filter]}%")
    end

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @blasts.to_json(:include => [:output, :owner, :entry])}
    end
  end

  # GET /blasts/1
  # GET /blasts/1.json
  def show
    @blast = Blast.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @blast }
    end
  end

  # GET /blasts/new
  # GET /blasts/new.json
  def new
    @blast = Blast.new
    @blast.owner_id = current_user.id

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @blast.to_json(:include => :output) }
    end
  end

  # GET /blasts/1/edit
  def edit
    @blast = Blast.find(params[:id])
  end

  # POST /blasts
  # POST /blasts.json
  def create
    @blast = Blast.new(params[:blast])
    @blast.start_at = Time.now
    @blast.owner_id = current_user.id
    @blast.status = "pending"

    respond_to do |format|
      if @blast.save
        format.html { redirect_to blasts_path, notice: 'Blast was successfully created.' }
        format.json { render json: @blast, status: :created, location: @blast }
      else
        format.html { render action: "new" }
        format.json { render json: @blast.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /blasts/1
  # PUT /blasts/1.json
  def update
    @blast = Blast.find(params[:id])

    respond_to do |format|
      if @blast.update_attributes(params[:blast])
        format.html { redirect_to blasts_path, notice: 'Blast was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @blast.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /blasts/1
  # DELETE /blasts/1.json
  def destroy
    @blast = Blast.find(params[:id])
    @blast.destroy

    respond_to do |format|
      format.html { redirect_to blasts_url }
      format.json { head :no_content }
    end
  end
  
  private
  def load_contents
    @contents = Content.accessible_by(current_ability)
  end
  
end
